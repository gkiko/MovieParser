package ge.kuku.movie.parse.core;

import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.ImoviesEntity;
import ge.kuku.movie.parse.data.Parser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("parse")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ParserService {

    public Parser getParser() {
        return CompositeParser.instance();
    }

    @POST
    @Path("{id}")
    public List<MovieDo> parse(@PathParam("id") String id, @NotNull @Valid MovieDo movieDo) throws IOException {
        List<ImoviesEntity> movieList = getParser().parse(movieDo.getName(), id);
        if (movieList == null) {
            throw new WebApplicationException(404);
        }

        List<MovieDo> doList = new ArrayList<>();
        for (ImoviesEntity imoviesEntity : movieList) {
            MovieDo movieDo1 = imoviesEntity.toMovieDo();
            movieDo1.setName(movieDo.getName());
            movieDo1.setImdbId(id);
            doList.add(movieDo1);
        }
        return doList;
    }

}
