package ru.votesystem;

import ru.votesystem.model.Role;
import ru.votesystem.model.User;
import ru.votesystem.web.json.JsonUtil;

import static ru.votesystem.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "password");
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "newUser", "new@gmail.com", "newpass", Role.USER);
    }

    public static User getUpdated() {
        return new User(USER_ID, "newName", "updated@gmail.com", "updatedpassword", Role.USER);
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
