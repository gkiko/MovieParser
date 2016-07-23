import ge.kuku.movie.parse.core.ParserService;
import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.OmdbClient;
import ge.kuku.movie.parse.data.Parser;

class FakeParserService extends ParserService {

    @Override
    public Parser getParser() {
    	CompositeParser p = CompositeParser.instance();
    	p.add(FakeParser.instance());
        return p;
    }

    @Override
    public OmdbClient getOmdbClient() {
        return FakeOmdbClient.instance();
    }
}