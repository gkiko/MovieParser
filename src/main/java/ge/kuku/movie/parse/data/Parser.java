package ge.kuku.movie.parse.data;

import java.io.IOException;
import java.util.List;

public interface Parser {

    List parse(String movieName, String imdbId) throws IOException;
}
