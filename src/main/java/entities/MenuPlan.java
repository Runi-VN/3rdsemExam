package entities;

import dto.MenuPlanDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name = "MenuPlan.getAll", query = "SELECT p FROM MenuPlan p"),
    @NamedQuery(name = "MenuPlan.deleteAllRows", query = "DELETE FROM MenuPlan"),
    @NamedQuery(name = "MenuPlan.getMenuPlanByID", query = "SELECT p FROM MenuPlan p WHERE p.id = :id"),})
public class MenuPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<DayPlan> dayPlans = new ArrayList();
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Ingredient> shoppingList = new ArrayList();
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public MenuPlan() {
    }

    public MenuPlan(MenuPlanDTO p) {
        this.id = p.getId();
        this.dayPlans = p.getDailyPlans();
        this.shoppingList = p.getShoppingList();
        this.user = p.getCreator();
    }

    public MenuPlan(List<DayPlan> dayPlans, User user) {
        this.dayPlans = dayPlans;
         for (DayPlan day : dayPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
        this.user = user;
    }

    /**
     * For use with testing
     * @param id
     * @param dayPlans
     * @param shoppingList
     * @param user 
     */
    public MenuPlan(List<DayPlan> dayPlans, List<Ingredient> shoppingList, User user) {
        this.dayPlans = dayPlans;
        this.shoppingList = shoppingList;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void addDayPlan(DayPlan dayPlan) {
        this.dayPlans.add(dayPlan);
    }

    /**
     * Set the value of dayPlans
     *
     * @param dayPlans new value of dayPlans
     */
    public void setDayPlans(List<DayPlan> dayPlans) {
        this.dayPlans = dayPlans;
        for (DayPlan day : dayPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.dayPlans);
        hash = 97 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuPlan other = (MenuPlan) obj;
        if (!Objects.equals(this.dayPlans, other.dayPlans)) {
            return false;
        }
        if (!Objects.equals(this.shoppingList, other.shoppingList)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MenuPlan{" + "id=" + id + ", dayPlans=" + dayPlans + ", shoppingList=" + shoppingList + ", user=" + user + '}';
    }

}
