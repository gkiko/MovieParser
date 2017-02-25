package ge.kuku.movie.parse.core;

import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.ImoviesEntity;
import ge.kuku.movie.parse.data.OmdbClient;
import ge.kuku.movie.parse.data.Parser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("movies")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ParserService {

    public Parser getParser() {
        return CompositeParser.instance();
    }

    public OmdbClient getOmdbClient() {
        return new OmdbClient();
    }

    @GET
    @Path("{id}")
    public List<MovieDo> parse(@PathParam("id") String id) throws Exception {
        String imdbMovieName = getOmdbClient().getMovieNameById(id);
        if (imdbMovieName == null) {
            throw new WebApplicationException(404);
        }
        List<ImoviesEntity> movieList = getParser().parse(imdbMovieName, id);
        if (movieList == null || movieList.isEmpty()) {
            throw new WebApplicationException(404);
        }

        List<MovieDo> doList = new ArrayList<>();
        for (ImoviesEntity imoviesEntity : movieList) {
            MovieDo movieDo1 = imoviesEntity.toMovieDo();
            movieDo1.setName(imdbMovieName);
            movieDo1.setImdbId(id);
            doList.add(movieDo1);
        }
        return doList;
    }

}
