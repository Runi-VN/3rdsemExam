 package facades;

import java.util.List;

/**
 *
 * @author Runi
 */
public class main {
    
    public static void main(String[] args)  {
        RecipeFacade facade = RecipeFacade.getRecipeFacade();
        List<String> allRecipes = facade.getAllRecipes();
        for (String allRecipe : allRecipes) {
            System.out.println(allRecipe);
        }
        System.out.println("__________________");
        for (String allRecipe : allRecipes) {
            System.out.println(facade.getSingleRecipe(allRecipe));
        }
    }
    
}