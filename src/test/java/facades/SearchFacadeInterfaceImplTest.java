package facades;

import dto.MenuPlanDTO;
import entities.DayPlan;
import entities.Ingredient;
import entities.MenuPlan;
import entities.Recipe;
import entities.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import utils.EMF_Creator;

@Disabled
public class SearchFacadeInterfaceImplTest {

    private static EntityManagerFactory emf;
    private static SearchFacadeInterfaceImpl facade;

    private static User u1;
    private static User u2;
    private static User u3;
    private static MenuPlan m1;
    private static MenuPlan m2;
    private static DayPlan d1;
    private static DayPlan d2;
    private static DayPlan d3;
    private static DayPlan d4;
    private static DayPlan d5;
    private static DayPlan d6;
    private static DayPlan d7;
    private static Ingredient ing1;
    private static Ingredient ing2;
    private static Ingredient ing3;
    private static Recipe r1;
    private static Recipe r2;
    private static Recipe r3;
    private static List<String> steps1;
    private static List<String> steps2;
    private static List<String> steps3;
    private static List<DayPlan> dayplans1;
    private static List<DayPlan> dayplans2;

    public SearchFacadeInterfaceImplTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = SearchFacadeInterfaceImpl.getSearchFacadeInterfaceImpl(emf);
        u1 = new User("user", "user");
        u2 = new User("admin", "admin");
        u3 = new User("both", "both"); //not used

        ing1 = new Ingredient("Carrots");
        ing2 = new Ingredient("Shallots");
        ing3 = new Ingredient("Beef");

        steps1 = new ArrayList();
        steps1.add("Wash stuff");
        steps1.add("Cut stuff");
        steps1.add("Eat stuff");

        steps2 = new ArrayList();
        steps2.add("Invite guests");
        steps2.add("Prepare food");
        steps2.add("Serve food");

        steps3 = new ArrayList();
        steps3.add("Clean the house");
        steps3.add("Walk the dog");
        steps3.add("Serve food from cans");

        r1 = new Recipe("Succesful Beefstew", "Put it all together", "idk, 5 hours?", steps1, Arrays.asList(ing1));
        r2 = new Recipe("Party of a lifetime", "Buy takeaway", "1 hour | 30 minutes if you get it yourself", steps2, Arrays.asList(ing2, ing3));
        r3 = new Recipe("Serving food with pets around", "Slam some canned food on the table, we love it", "5 minutes", steps3, Arrays.asList(ing3));

        d1 = new DayPlan(r1, DayPlan.WeekDays.MON);
        d2 = new DayPlan(r1, DayPlan.WeekDays.TUE);
        d3 = new DayPlan(r2, DayPlan.WeekDays.WED);
        d4 = new DayPlan(r3, DayPlan.WeekDays.THU);
        d5 = new DayPlan(r2, DayPlan.WeekDays.FRI);
        d6 = new DayPlan(r3, DayPlan.WeekDays.SAT);
        d7 = new DayPlan(r1, DayPlan.WeekDays.SUN);

        dayplans1 = new ArrayList();
        dayplans1.add(d1);
        dayplans1.add(d2);
        dayplans1.add(d3);
        dayplans1.add(d4);
        dayplans1.add(d5);
        dayplans1.add(d6);
        dayplans1.add(d7);
        dayplans2 = new ArrayList();
        dayplans2.add(d5);
        dayplans2.add(d6);
        dayplans2.add(d7);

        m1 = new MenuPlan(dayplans1, u1);
        m2 = new MenuPlan(dayplans2, u2);

        m1.setId(1);
        m2.setId(2);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(m1);
        em.persist(m2);
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("MenuPlan.deleteAllRows").executeUpdate();
            em.createNamedQuery("Ingredient.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Test of GetMenuPlan method, of class SearchFacadeInterfaceImpl.
     */
    @Test
    public void testGetMenuPlan() {
        System.out.println("GetMenuPlan");
        int id = m2.getId();
        MenuPlanDTO expResult = new MenuPlanDTO(m2);
        MenuPlanDTO result = facade.GetMenuPlan(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllMenuPlans method, of class SearchFacadeInterfaceImpl.
     */
    @Test
    public void testGetAllMenuPlans() {
        System.out.println("getAllMenuPlans");
        SearchFacadeInterfaceImpl instance = null;
        List<MenuPlanDTO> expResult = null;
        List<MenuPlanDTO> result = facade.getAllMenuPlans();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userAddMenuPlan method, of class SearchFacadeInterfaceImpl.
     */
    @Test
    public void testUserAddMenuPlan() {
        System.out.println("userAddMenuPlan");
        MenuPlanDTO menuPlan = null;
        SearchFacadeInterfaceImpl instance = null;
        MenuPlanDTO expResult = null;
        MenuPlanDTO result = facade.userAddMenuPlan(menuPlan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userEditMenuPlan method, of class SearchFacadeInterfaceImpl.
     */
    @Test
    public void testUserEditMenuPlan() {
        System.out.println("userEditMenuPlan");
        MenuPlanDTO menuPlan = null;
        SearchFacadeInterfaceImpl instance = null;
        MenuPlanDTO expResult = null;
        MenuPlanDTO result = facade.userEditMenuPlan(menuPlan);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userDeleteMenuPlan method, of class SearchFacadeInterfaceImpl.
     */
    @Test
    public void testUserDeleteMenuPlan() {
        System.out.println("userDeleteMenuPlan");
        int id = 0;
        SearchFacadeInterfaceImpl instance = null;
        MenuPlanDTO expResult = null;
        MenuPlanDTO result = facade.userDeleteMenuPlan(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
