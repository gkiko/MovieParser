import ge.kuku.movie.parse.core.MovieDo;
import ge.kuku.movie.parse.data.ImoviesEntity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestParserService extends JerseyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Override
    protected Application configure() {
        return new ResourceConfig(FakeParserService.class);
    }

    @Test
    public void expect_404() {
        FakeParser.instance().clear();

        Response actual = target("movies/someId")
                .request()
                .get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), actual.getStatus());
    }

    @Test
    public void correctParams() {
        FakeParser.instance().clear();

        ImoviesEntity entity = new ImoviesEntity();
        entity.setMovieName("Interstellar");
        entity.setImdbId("tt0816692");

        FakeOmdbClient.instance().setNameToReturn("Interstellar");

        FakeParser.instance().addItem("Interstellar", entity);

        MovieDo[] actual = target("movies/tt0816692")
                .request()
                .get()
                .readEntity(MovieDo[].class);
        assertTrue(actual.length == 1);
    }
}