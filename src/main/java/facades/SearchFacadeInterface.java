package facades;

import dto.MenuPlanDTO;
import java.util.List;

public interface SearchFacadeInterface {
    
    public MenuPlanDTO GetMenuPlan(int id); 
    
    public List<MenuPlanDTO> getAllMenuPlans();
    
    public MenuPlanDTO userAddMenuPlan(MenuPlanDTO menuPlan);
    
    public MenuPlanDTO userEditMenuPlan(MenuPlanDTO menuPlan);
    
    public MenuPlanDTO userDeleteMenuPlan(int id);
    
}
