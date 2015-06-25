package ge.kuku.movie.parse.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompositeParser implements Parser {

    private static CompositeParser parser;
    private List<Parser> childParsers = new ArrayList<>();

    public static CompositeParser instance() {
        if (parser == null) {
            parser = new CompositeParser();
        }
        return parser;
    }

    @Override
    public String parse(String movieName, String imdbId) throws IOException {
        for (Parser parser : childParsers) {
            String res = parser.parse(movieName, imdbId);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    public void add(Parser parser) {
        childParsers.add(parser);
    }

    public void remove(Parser parser) {
        childParsers.remove(parser);
    }

    public void removeAll() {
        childParsers.clear();
    }
}
