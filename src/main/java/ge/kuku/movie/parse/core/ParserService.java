package ge.kuku.movie.parse.core;

import ge.kuku.movie.parse.data.CompositeParser;
import ge.kuku.movie.parse.data.Parser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("parse")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ParserService {

    public Parser getParser() {
        return CompositeParser.instance();
    }

    @POST
    @Path("{id}")
    public MovieDo parse(@PathParam("id") String id, @NotNull @Valid MovieDo movieDo) {
        String res = getParser().parse();
        if (res == null) {
            throw new WebApplicationException(400);
        }
        MovieDo found = new MovieDo();
        found.setName("asd");
        return found;
    }

}