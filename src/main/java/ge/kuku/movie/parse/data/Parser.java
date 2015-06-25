package ge.kuku.movie.parse.data;

import java.io.IOException;

public interface Parser {

    String parse(String movieName, String imdbId) throws IOException;
}
