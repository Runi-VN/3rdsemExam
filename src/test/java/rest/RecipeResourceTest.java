package rest;

import entities.Ingredient;
import entities.Recipe;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import static io.restassured.RestAssured.get;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author Runi
 */
public class RecipeResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Recipe r1;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    public RecipeResourceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        // Grizzly Start
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        // Grizzly End
        
        r1 = new Recipe();
        r1.setName("slow cooker beef stew");
        r1.setDescription("Moist and juicy beef stew made in a slow cooker with carrots, onion and potatoes. ");
        r1.setPrep_time("Prep: 20 min |Cook: 9 hours ");
        List<String> steps = r1.getPreparation_steps();
        steps.add("Take the beef and season with salt and pepper to taste. Brown on all sides in a large frying pan over high heat.");
        steps.add("Place in the slow cooker and add the soup mix, water, carrots, onion, potatoes and celery.");
        steps.add("Cover and cook on low setting for 8 to 10 hours.");
        r1.setPreparation_steps(steps);
        List<Ingredient> ingredients = r1.getIngredients();
        ingredients.add(new Ingredient("1.8kg beef stewing steak"));
        ingredients.add(new Ingredient("salt and pepper to taste"));
        ingredients.add(new Ingredient("1 sachet dried French onion soup mix"));
        ingredients.add(new Ingredient("240ml water"));
        ingredients.add(new Ingredient("3 carrots, chopped"));
        ingredients.add(new Ingredient("1 onion, chopped"));
        ingredients.add(new Ingredient("3 potatoes, peeled and cubed"));
        ingredients.add(new Ingredient("1 stalk celery, chopped"));
        r1.setIngredients(ingredients);
    }

    @AfterAll
    public static void tearDownClass() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @Test
    public void serverIsRunning() {
        System.out.println("Testing is server UP");
        given().when().get("/recipes").then().statusCode(200);
    }

    /**
     * Test of getAllRecipes method, of class RecipeResource.
     */
    @Test
    public void testGetAllRecipes() throws Exception {
        System.out.println("getAllRecipesREST");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .when()
                .get("/recipes/all")
                .then()
                .statusCode(200).assertThat().body("[0]", equalTo("Slow cooker spicy chicken and bean soup"));
        
    }

    /**
     * Test of getSingleRecipe method, of class RecipeResource.
     */
    @Test
    public void testGetSingleRecipe() throws Exception {
        System.out.println("getSingleRecipeREST");
        String recipe = "slow cooker beef stew";
        Recipe expResult = r1;
        Recipe result = get("/recipes/specific/" + recipe).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .extract()
                .as(Recipe.class);
        assertEquals(expResult, result);
        
    }

}
