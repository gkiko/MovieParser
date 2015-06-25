import ge.kuku.movie.parse.data.Parser;

public class FakeParser implements Parser {

    @Override
    public String parse(String movieName, String imdbId) {
        return "asd";
    }
}
