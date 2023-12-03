package ru.votesystem;

import ru.votesystem.model.Dish;

import static ru.votesystem.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 5;
    public static final int DISH2_ID = DISH1_ID + 1;
    public static final int DISH3_ID = DISH1_ID + 2;
    public static final int DISH4_ID = DISH1_ID + 3;
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final Dish dish1 = new Dish(DISH1_ID, 100, "яичница");
    public static final Dish dish2 = new Dish(DISH2_ID, 250, "картофель фри");
    public static final Dish dish3 = new Dish(DISH3_ID, 300, "бургер");
    public static final Dish dish4 = new Dish(DISH4_ID, 500, "пюре с котлетой");

    public static Dish getNew() {
        return new Dish(null, 1000, "new Dish");
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, dish1.getPrice() + 100, "updatedDish");
    }
}
