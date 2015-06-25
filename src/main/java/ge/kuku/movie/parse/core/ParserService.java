package ge.kuku.movie.parse.core;

import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.Parser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("parse")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ParserService {

    public Parser getParser() {
        return CompositeParser.instance();
    }

    @POST
    @Path("{id}")
    public MovieDo parse(@PathParam("id") String id, @NotNull @Valid MovieDo movieDo) throws IOException {
        String res = getParser().parse(movieDo.getName(), id);
        if (res == null) {
            throw new WebApplicationException(404);
        }
        movieDo.setName(movieDo.getName());
        movieDo.setId(id);
        movieDo.setSource(res);
        return movieDo;
    }

}