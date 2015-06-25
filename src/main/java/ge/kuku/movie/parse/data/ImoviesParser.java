package ge.kuku.movie.parse.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImoviesParser implements Parser {

    private String jsonApi = "http://www.imovies.ge/services/films/search_new.json.php?term=";

    @Override
    public String parse(String movieName, String imdbId) throws IOException {
        List<String> urls = possibleUrlsContaining(movieName);
        return findOnPages(imdbId, urls);
    }

    private List<String> possibleUrlsContaining(String movieName) throws IOException {
        List<String> urls = new ArrayList<>();
        URL url = new URL(jsonApi+movieName);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(url.openStream());
        for (JsonNode single : actualObj) {
            urls.add(single.get("url").asText());
        }
        return urls;
    }

    private String findOnPages(String imdbId, List<String> urls) throws IOException {
        for (String url : urls) {
            String found = find(imdbId, url);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private String find(String imdbId, String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element div = doc.select(".imdbtop").first();
        Elements links = div.select("a[href]");

        for (Element link : links) {
            String strLink = link.attr("href");
            if (strLink.contains("imdb.com/title")) {
                String foundId = strLink.substring(strLink.lastIndexOf("/")+1);
                if (foundId.equals(imdbId)) {
                    return searchForVideoSource(doc);
                }
            }
        }
        return null;
    }

    private String searchForVideoSource(Document doc) {
        Element videoTag = doc.select("video").first();
        Element sourceTag = videoTag.select("source").first();

        return sourceTag.attr("src");
    }
}
