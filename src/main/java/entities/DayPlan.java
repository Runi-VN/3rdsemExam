package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DayPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Recipe recipe;
    private WeekDays day_of_week;

    public DayPlan() {
    }

    public DayPlan(Recipe recipe, WeekDays day_of_week) {
        this.recipe = recipe;
        this.day_of_week = day_of_week;
    }

    /**
     * Get the value of recipe
     *
     * @return the value of recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Set the value of recipe
     *
     * @param recipe new value of recipe
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public WeekDays getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(WeekDays day_of_week) {
        this.day_of_week = day_of_week;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static enum WeekDays {

        MON("Monday", 1), TUE("Tuesday", 2), WED("Wednesday", 3), THU("Thursday ", 4), FRI("Friday", 5), SAT("Saturday", 6), SUN("Sunday", 7);

        private String weekdayName;
        private int weekdayNumber;

        private WeekDays(String name, int dayNumber) {
            this.weekdayName = name;
            this.weekdayNumber = dayNumber;
        }

        public String getWeekdayName() {
            return weekdayName;
        }

        public int getWeekdayNumber() {
            return weekdayNumber;
        }

        @Override
        public String toString() {
            return weekdayName;
        }
    }

}
