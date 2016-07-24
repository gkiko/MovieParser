import ge.kuku.movie.parse.data.ImoviesEntity;
import ge.kuku.movie.parse.data.Parser;

import java.util.*;

class FakeParser implements Parser {

    private static FakeParser parser;
    private Map<String, ImoviesEntity> inMemory;

    static FakeParser instance() {
        if (parser == null) {
            parser = new FakeParser();
        }
        return parser;
    }

    private FakeParser() {
        inMemory = new HashMap<>();
    }

    @Override
    public List<ImoviesEntity> parse(String movieName, String imdbId) {
        List<ImoviesEntity> l = new ArrayList<>();

        ImoviesEntity val = inMemory.get(movieName);
        if (val != null){
            l.add(val);
        }
        return l;
    }

    void addItem(String movieName, ImoviesEntity movieDo) {
        inMemory.put(movieName, movieDo);
    }

    void clear() {
        inMemory.clear();
    }
}