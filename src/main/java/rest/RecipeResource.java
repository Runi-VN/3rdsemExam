package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Recipe;
import errorhandling.APIRequestException;
import facades.RecipeFacade;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("recipes")
public class RecipeResource {

    private static final RecipeFacade FACADE = RecipeFacade.getRecipeFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World. This is the recipes part of the backend.\"}";
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<String> getAllRecipes() throws APIRequestException {
        return FACADE.getAllRecipes();
    }

    @GET
    @Path("specific/{recipe}")
    @Produces({MediaType.APPLICATION_JSON})
    public Recipe getSingleRecipe(@PathParam("recipe") String recipe) throws APIRequestException {
        return FACADE.getSingleRecipe(recipe);
    }

//    @GET
//    @Path("populate")
//    @Produces({MediaType.APPLICATION_JSON})
//    public String populateDatabase() {
//
//        boolean success = FACADE.populate();
//
//        if (success) {
//            return "{\"message\":\"Database populated with dummy data\"}";
//        } else {
//            return "{\"message\":\"Failed to populate database\"}";
//        }
//    }

}
