package idv.code.dto;

/**
 * Created by weide on 2017-07-17.
 */
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final DishType dishType;

    public Dish(String name, boolean vegetarian, int calories, DishType dishType) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.dishType = dishType;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public DishType getDishType() {
        return dishType;
    }

    @Override
    public String toString() {
        return String.format("%s %b %d %s", name, vegetarian, calories, dishType);
    }
}
