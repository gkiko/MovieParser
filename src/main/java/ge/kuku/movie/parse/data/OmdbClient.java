package ge.kuku.movie.parse.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;

public class OmdbClient {

    private static final String OMDB_API = "http://www.omdbapi.com/";

    public String getMovieNameById(String imdbId) throws Exception {
        String jsonBody = Unirest.get(OMDB_API).queryString("i", imdbId).asString().getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(jsonBody);
        return actualObj.get("Title").asText();
    }

}
