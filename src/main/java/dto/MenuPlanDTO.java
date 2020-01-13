package dto;

import entities.DayPlan;
import entities.Ingredient;
import entities.MenuPlan;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuPlanDTO {

    private int id;
    private List<DayPlan> dailyPlans = new ArrayList();
    private List<Ingredient> shoppingList = new ArrayList();
    private User creator;

    public MenuPlanDTO() {
    }
    
    public MenuPlanDTO(MenuPlan p) {
        this.id = p.getId();
        this.dailyPlans = p.getDayPlans();
        this.shoppingList = p.getShoppingList();
        this.creator = p.getUser();
    }

    public MenuPlanDTO(int id, List<DayPlan> dayPlans, List<Ingredient> shoppingList, User user) {
        this.id = id;
        this.dailyPlans = dayPlans;
        for (DayPlan day : dayPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
        this.creator = user;
    }

    public MenuPlanDTO(List<DayPlan> dayPlans, List<Ingredient> shoppingList, User user) {
        this.dailyPlans = dayPlans;
        for (DayPlan day : dayPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
        this.creator = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<DayPlan> getDailyPlans() {
        return dailyPlans;
    }

    public void setDailyPlans(List<DayPlan> dailyPlans) {
        this.dailyPlans = dailyPlans;
        for (DayPlan day : dailyPlans) {
            for (Ingredient ing : day.getRecipe().getIngredients()) {
                this.shoppingList.add(ing);
            }
        }
    }

    public List<Ingredient> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Ingredient> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.dailyPlans);
        hash = 19 * hash + Objects.hashCode(this.shoppingList);
        hash = 19 * hash + Objects.hashCode(this.creator);
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
        final MenuPlanDTO other = (MenuPlanDTO) obj;
        if (!Objects.equals(this.dailyPlans, other.dailyPlans)) {
            return false;
        }
        if (!Objects.equals(this.shoppingList, other.shoppingList)) {
            return false;
        }
        if (!Objects.equals(this.creator, other.creator)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MenuPlanDTO{" + "id=" + id + ", dailyPlans=" + dailyPlans + ", shoppingList=" + shoppingList + ", creator=" + creator + '}';
    }
}
