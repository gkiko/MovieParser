package ge.kuku.movie.parse.core;

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

    // @GET
    // public List<MovieDo> read() {
    //     final ArrayList<MovieDo> result = new ArrayList<MovieDo>();
    //     MovieDo movieDo = new MovieDo();
    //     movieDo.setSource("http://example.com/movie/nasjdhiu13h12g");
    //     movieDo.setLanguage("JP");
    //     movieDo.setQuality("HD");
    //     result.add(movieDo);
    //     return result;
    // }

    // @GET
    // @Path("{id}")
    // public List<MovieDo> retrieve(@PathParam("id") String id) {
    //     List<MovieDo> movieDos = new ArrayList<>();
    //     List<MovieItem> items = getRepo().retrieve(id);
    //     for (MovieItem fromDb : items) {
    //         movieDos.add(fromDb.toDo());
    //     }
    //     return movieDos;
    // }

}