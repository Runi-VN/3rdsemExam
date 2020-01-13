package facades;

import dto.MenuPlanDTO;
import entities.MenuPlan;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;

public class SearchFacadeInterfaceImpl implements SearchFacadeInterface {

    private static SearchFacadeInterfaceImpl facade;
    private static EntityManagerFactory emf;

    private SearchFacadeInterfaceImpl() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static SearchFacadeInterfaceImpl getSearchFacadeInterfaceImpl(EntityManagerFactory _emf) {
        if (facade == null) {
            emf = _emf;
            facade = new SearchFacadeInterfaceImpl();
        }
        return facade;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public MenuPlanDTO GetMenuPlan(int id) {
        EntityManager em = getEntityManager();
        try {
            return new MenuPlanDTO(em.createNamedQuery("MenuPlan.getMenuPlanByID", MenuPlan.class).setParameter("id", id).getSingleResult());
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException("No menu with that ID exists", 404);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), 400);
        }
    }

    @Override
    public List<MenuPlanDTO> getAllMenuPlans() {
        EntityManager em = getEntityManager();
        try {
            List<MenuPlan> menus = em.createNamedQuery("MenuPlan.getAll").getResultList();
            List<MenuPlanDTO> result = new ArrayList();
            for (MenuPlan m : menus) {
                result.add(new MenuPlanDTO(m));
            }
            return result;
        } catch (NoResultException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("No menus found", 404);
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new WebApplicationException(ex.getMessage(), 400);
        } finally {
            em.close();
        }
    }

    @Override
    public MenuPlanDTO userAddMenuPlan(MenuPlanDTO menuPlan) {
        if (menuPlan == null || menuPlan.getDailyPlans() == null
                || menuPlan.getShoppingList() == null || menuPlan.getCreator() == null) {
            throw new WebApplicationException("Invalid menu input", 400);
        }
        MenuPlan result = new MenuPlan(menuPlan);
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            return new MenuPlanDTO(result);
        } catch (EntityExistsException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Menu already exists", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while adding menu", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public MenuPlanDTO userEditMenuPlan(MenuPlanDTO menuPlan) {
       if (menuPlan == null || menuPlan.getDailyPlans() == null
                || menuPlan.getShoppingList() == null || menuPlan.getCreator() == null) {
            throw new WebApplicationException("Invalid menu input", 400);
        }
        MenuPlan result = new MenuPlan(menuPlan);
        EntityManager em = getEntityManager();
        try {
            //em.find(MenuPlan.class, result.getId());
            em.getTransaction().begin();
            result = em.merge(result);
            em.getTransaction().commit();
            return new MenuPlanDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Menu does not comply with standards", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while editing menu", 500);
        } finally {
            em.close();
        }
    }

    @Override
    public MenuPlanDTO userDeleteMenuPlan(int id) {
         MenuPlan result;
        EntityManager em = getEntityManager();
        try {
            result = em.find(MenuPlan.class, id);
            em.getTransaction().begin();
            em.remove(result);
            em.getTransaction().commit();
            return new MenuPlanDTO(result);
        } catch (IllegalArgumentException e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Menu/Menu ID does not comply with standards", 400);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new WebApplicationException("Something went wrong while deleting menu", 500);
        } finally {
            em.close();
        }
    }
}
