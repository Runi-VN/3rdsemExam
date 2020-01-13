package facades;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import entities.Ingredient;
import entities.Recipe;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.WebApplicationException;

/**
 *
 * Retrieves a list of recipes from other servers
 */
public class RecipeFacade {

    private static final String URL = "http://46.101.217.16:4000/";
    private static final String allRecipesURLEXTENSION = "allRecipes";
    private static final String singleRecipeURLEXTENSION = "recipe/";
    private Gson GSON = new Gson();

    private static RecipeFacade facade;

    private RecipeFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static RecipeFacade getRecipeFacade() {
        if (facade == null) {
            facade = new RecipeFacade();
        }
        return facade;
    }

    /**
     * Returns a list of Strings from the endpoint serving a list of recipes.
     *
     * Used as a proxy service and as for display later in the frontend.
     *
     * @return
     * @throws APIRequestException
     */
    public List<String> getAllRecipes() {
        List<String> recipeList = new ArrayList();
        String allRecipeData = getAPIData(allRecipesURLEXTENSION);
        JsonArray recipeStringArray = GSON.fromJson(allRecipeData, JsonArray.class);
        recipeStringArray.forEach(recipe -> recipeList.add(recipe.getAsString()));
        return recipeList;
    }

    /**
     * Returns a single recipe based on a searchTerm, e.g. the recipe name.
     *
     * Used as a proxy service and later for the frontend.
     *
     * @param searchTerm recipe ID, which is a long string with spaces and all
     * @return
     */
    public Recipe getSingleRecipe(String searchTerm) {
        String recipeData = getAPIData(singleRecipeURLEXTENSION + searchTerm);
        JsonObject obj = GSON.fromJson(recipeData, JsonObject.class);
        //return GSON.fromJson(recipeData, Recipe.class); //easy solution, but I dont want my ID to be a string with spaces in it, so I have to manually do it.
        Recipe result = new Recipe();
        result.setName(obj.get("id").getAsString());
        result.setDescription(obj.get("description").getAsString());
        result.setPrep_time(obj.get("prep_time").getAsString());
        for (JsonElement prepStep : obj.get("preparaion_steps").getAsJsonArray()) {
            //spelling error making the beforementioned solution even harder
            result.getPreparation_steps().add(prepStep.getAsString());
        }
        for (JsonElement ingredient : obj.get("ingredients").getAsJsonArray()) {
            result.getIngredients().add(new Ingredient(ingredient.getAsString()));
        }
        return result;
    }

    private String getAPIData(String urlExtension) {
        if (urlExtension.contains(" ")) {
            urlExtension = urlExtension.replaceAll(" ", "%20"); //replace spaces with %20 which works with the provided endpoint (as connection/urlBuilder does NOT work with spaces)
        }
        try {
            URL siteURL = new URL(URL + urlExtension);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("user-agent", "Application");
            try (Scanner scan = new Scanner(connection.getInputStream(), "UTF-8")) {
                String response = "";
                while (scan.hasNext()) {
                    response += scan.nextLine();
                }
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(response);
                if (jsonElement.isJsonObject()) {
                    return GSON.fromJson(response, JsonObject.class).toString();
                } else if (jsonElement.isJsonArray()) {
                    return GSON.fromJson(response, JsonArray.class).toString();
                }
                return response;
            }
        } catch (JsonSyntaxException | IOException e) {
            throw new WebApplicationException("Could not retrieve requested object from resource: " + URL + urlExtension + ". ERROR: " + e, 404);
        } 
    }
}
