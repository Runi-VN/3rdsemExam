package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class MenuPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany
    private List<DayPlan> dayPlans;
    private List<Ingredient> shoppingList;
    @ManyToOne
    private User user;

    public MenuPlan() {
    }

    public MenuPlan(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
        for (DayPlan day : dayPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
    }

    /**
     * Get the value of shoppingList
     *
     * @return the value of shoppingList
     */
    public List<Ingredient> getShoppingList() {
        return shoppingList;
    }

    /**
     * Set the value of shoppingList
     *
     * @param shoppingList new value of shoppingList
     */
    public void setShoppingList(List<Ingredient> shoppingList) {
        this.shoppingList = shoppingList;
    }


    /**
     * Get the value of dayPlans
     *
     * @return the value of dayPlans
     */
    public List<DayPlan> getDayPlans() {
        return dayPlans;
    }
    
    public void addDayPlan(DayPlan dayPlan){
        this.dayPlans.add(dayPlan);
    }

    /**
     * Set the value of dayPlans
     *
     * @param dayPlans new value of dayPlans
     */
    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
