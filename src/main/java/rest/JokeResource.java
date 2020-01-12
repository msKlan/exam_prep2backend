package rest;

import facades.ApiFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
//import com.google.gson.Gson;

/**
 *
 * @author Klan
 */
@Path("jokeByCategoryV2")
public class JokeResource {

    private static final ApiFacade FACADE =  ApiFacade.getApiFacade();

    /**
     * Creates a new instance of JokeResource
     */
    public JokeResource() {
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJson() {
//        return "{\"msg\": \"Welcome to the exam!\"}";
//    }

    @GET
    @Path("/{categories}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String getJsonPeopleList(@PathParam("categories") String categories) {
        List aList= new ArrayList(Arrays.asList(categories.split(",")));
        String url = "https://api.chucknorris.io/jokes/random?category=";
        
        return FACADE.fetch(url, aList).toString();
    }

}
