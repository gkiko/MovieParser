package ge.kuku.movie.parse.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

public class OMDBUtils {

    private static final String OMDBAPI = "http://www.omdbapi.com/?i=";

    public OMDBUtils() {}

    public String getMovieNameById(String imdbId) throws IOException {
        String urlString = String.format("%s%s", OMDBAPI, URLEncoder.encode(imdbId, "UTF-8"));
        URL url = new URL(urlString);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(url.openStream());
        return actualObj.get("Title").asText();
    }

}
