import ge.kuku.movie.parse.core.MovieDo;
import ge.kuku.movie.parse.data.ImoviesEntity;
import ge.kuku.movie.parse.data.Parser;

import java.util.*;

public class FakeParser implements Parser {

    Map inMemory = new HashMap<>();

    @Override
    public List parse(String movieName, String imdbId) {
        ImoviesEntity val = (ImoviesEntity) inMemory.get(movieName);
        if (val == null){
            return Collections.emptyList();
        }
        List l = new ArrayList();
        l.add(val);
        return l;
    }

    public void addItem(String movieName, ImoviesEntity movieDo) {
        inMemory.put(movieName, movieDo);
    }
}
