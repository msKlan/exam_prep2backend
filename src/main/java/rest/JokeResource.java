package rest;

import facades.ApiFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Klan
 */
@Path("abc")
public class JokeResource {

    private static final ApiFacade FACADE =  ApiFacade.getApiFacade();

    /**
     * Creates a new instance of JokeResource
     */
    public JokeResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "{\"msg\": \"Welcome to the exam!\"}";
    }

    @GET
    @Path("joke/{categories}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonPeopleList(@PathParam("categories") String categories) {
        String url = "https://api.chucknorris.io/jokes/random?category=";
        List<String> l = new ArrayList();
        l.add("food");
        l.add("fashion");
//        l.add("people/3/");
//        l.add("people/4/");
//        l.add("people/5/");
        return FACADE.fetch(url, l).toString();
    }

}
