import ge.kuku.movie.parse.core.MovieDo;
import ge.kuku.movie.parse.core.ParserService;
import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.ImoviesEntity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestParserService extends JerseyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Override
    protected Application configure() {
        return new ResourceConfig(FakeParseService.class);
    }

    @Test
    public void expect_400() {
        Response actual = target("parse/someId").request().post(Entity.entity(null, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), actual.getStatus());
    }

    @Test
    public void expect_400_1() {
        MovieDo mDo = new MovieDo();
        Response actual = target("parse/someId").request().post(Entity.entity(mDo, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), actual.getStatus());
    }

    @Test
    public void correctParams() {
        CompositeParser.instance().removeAll();
        FakeParser parser = new FakeParser();
        parser.addItem("Interstellar", new ImoviesEntity());
        CompositeParser.instance().add(parser);

        MovieDo mDo = new MovieDo();
        mDo.setName("Interstellar");
        MovieDo[] actual = target("parse/tt0816692").request().post(Entity.entity(mDo, MediaType.APPLICATION_JSON)).readEntity(MovieDo[].class);
        assertFalse(actual.length == 0);
    }
}
