package ru.votesystem;

import ru.votesystem.model.Restaurant;

import static ru.votesystem.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes", "votes");
    public static final int REST1_ID = START_SEQ + 2;
    public static final int REST2_ID = START_SEQ + 3;
    public static final int REST3_ID = START_SEQ + 4;
    public static final Restaurant rest1 = new Restaurant(REST1_ID, "shashlikoff");
    public static final Restaurant rest2 = new Restaurant(REST2_ID, "mishiko");
    public static final Restaurant rest3 = new Restaurant(REST3_ID, "adj");

    public static Restaurant getNew() {
        return new Restaurant(null, "new Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(REST1_ID, "newName");
    }
}
