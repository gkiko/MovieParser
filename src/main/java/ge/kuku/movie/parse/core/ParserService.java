package ge.kuku.movie.parse.core;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("parse")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ParserService {

    @GET
    public Response ping() {
        return Response.ok().build();
    }

    @POST
    @Path("{id}")
    public MovieDo parse(@PathParam("id") String id, @NotNull @Valid MovieDo movieDo) {
        System.out.println(movieDo.getName());
        return new MovieDo();
    }

}