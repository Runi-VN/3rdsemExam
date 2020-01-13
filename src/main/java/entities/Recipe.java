package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private String prep_time;
    private List<String> preparation_steps = new ArrayList();
    private List<Ingredient> ingredients = new ArrayList();

    public Recipe() {
    }

    public Recipe(String name, String description, String prep_time, List<String> preparation_steps, List<Ingredient> ingredients) {
        this.name = name;
        this.description = description;
        this.prep_time = prep_time;
        this.preparation_steps = preparation_steps;
        this.ingredients = ingredients;
    }
    
    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of ingredients
     *
     * @return the value of ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Set the value of ingredients
     *
     * @param ingredients new value of ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Get the value of preparation_steps
     *
     * @return the value of preparation_steps
     */
    public List<String> getPreparation_steps() {
        return preparation_steps;
    }

    /**
     * Set the value of preparation_steps
     *
     * @param preparation_steps new value of preparation_steps
     */
    public void setPreparation_steps(List<String> preparation_steps) {
        this.preparation_steps = preparation_steps;
    }

    /**
     * Get the value of prep_time
     *
     * @return the value of prep_time
     */
    public String getPrep_time() {
        return prep_time;
    }

    /**
     * Set the value of prep_time
     *
     * @param prep_time new value of prep_time
     */
    public void setPrep_time(String prep_time) {
        this.prep_time = prep_time;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.prep_time);
        hash = 97 * hash + Objects.hashCode(this.preparation_steps);
        hash = 97 * hash + Objects.hashCode(this.ingredients);
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
        final Recipe other = (Recipe) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.prep_time, other.prep_time)) {
            return false;
        }
        if (!Objects.equals(this.preparation_steps, other.preparation_steps)) {
            return false;
        }
        if (!Objects.equals(this.ingredients, other.ingredients)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Recipe{" + "id=" + id + ", name=" + name + ", description=" + description + ", prep_time=" + prep_time + ", preparation_steps=" + preparation_steps + ", ingredients=" + ingredients + '}';
    }
}
