package ge.kuku.movie.parse.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImoviesParser implements Parser {

    private String jsonApi = "http://www.imovies.ge/services/films/search_new.json.php?term=";
    private String rssApi = "http://www.imovies.ge/get_playlist_jwQ_html5.php";

    @Override
    public List parse(String movieName, String imdbId) throws IOException {
        List<String> urls = possibleUrlsContaining(movieName);
        String pageUrl = whichPageContains(imdbId, urls);
        String imoviesId = getImoviesId(pageUrl);
        System.out.println(imoviesId);
        return parseRss(imoviesId);
    }

    private List<String> possibleUrlsContaining(String movieName) throws IOException {
        List<String> urls = new ArrayList<>();
        URL url = new URL(jsonApi+movieName.toLowerCase());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(url.openStream());
        for (JsonNode single : actualObj) {
            urls.add(single.get("url").asText());
        }
        return urls;
    }

    private String whichPageContains(String imdbId, List<String> urls) throws IOException {
        for (String url : urls) {
            if (find(imdbId, url)) {
                return url;
            }
        }
        return null;
    }

    private boolean find(String imdbId, String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element div = doc.select(".imdbtop").first();
        Elements links = div.select("a[href]");

        for (Element link : links) {
            String strLink = link.attr("href");
            if (strLink.contains("imdb.com/title")) {
                String foundId = strLink.substring(strLink.lastIndexOf("/")+1);
                if (foundId.equals(imdbId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getImoviesId(String pageUrl) {
        if (pageUrl == null) return null;
        int idIndex = pageUrl.lastIndexOf("/") + 1;
        return pageUrl.substring(idIndex);
    }

    private List parseRss(String imoviesId) {
        List res = null;
        try {
            String rssText = Unirest.get(rssApi).queryString("movie_id",imoviesId).asString().getBody();
            res = getSources(rssText);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return res;
    }

    Pattern containsAllRecords = Pattern.compile(".*<jwplayer:source.*");
    Pattern containsVideoLangData = Pattern.compile("(?<=lang=\\\")((.*?)(?=\\\"))");
    Pattern containsQualityData = Pattern.compile("(?<=label=\\\")((.*?)(?=\\\"))");

    private List getSources(String rssContent) {
        List<ImoviesEntity> movieList = new ArrayList<>();

        Matcher matchLinesToParse = containsAllRecords.matcher(rssContent);
        while (matchLinesToParse.find()) {
            String matched = matchLinesToParse.group();

            String quality = null;
            Matcher m = containsQualityData.matcher(matched);
            if (m.find()) {
                quality = m.group();
            }

            m = containsVideoLangData.matcher(matched);
            while (m.find()) {
                String videoData = m.group();
                List<ImoviesEntity> sources = getVideoSourceAndLang(videoData);
                for (ImoviesEntity entity : sources) {
                    entity.setQuality(quality);
                    movieList.add(entity);
                }
            }
        }

        return movieList;
    }

    private List getVideoSourceAndLang(String str) {
        List list = new ArrayList<>();
        String[] langsAndSources = str.split(",");
        for (String langAndSource : langsAndSources) {
            String[] arr = langAndSource.split("\\|");
            String language = arr[0];
            String videoSrc = arr[1];
            ImoviesEntity imoviesEntity = new ImoviesEntity();
            imoviesEntity.setLanguage(language);
            imoviesEntity.setSource(videoSrc);
            list.add(imoviesEntity);
        }
        return list;
    }
}
