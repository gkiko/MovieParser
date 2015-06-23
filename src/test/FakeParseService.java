import ge.kuku.movie.parse.core.ParserService;
import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.Parser;

public class FakeParseService extends ParserService {

    @Override
    public Parser getParser() {
        return CompositeParser.instance();
    }
}
