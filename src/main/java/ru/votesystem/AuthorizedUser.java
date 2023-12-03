package ru.votesystem;


import ru.votesystem.model.User;
import ru.votesystem.to.UserTo;

import java.io.Serial;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        setTo(new UserTo(user.id(),user.getName(),user.getEmail(),user.getPassword()));
    }

    public int getId() {
        return userTo.id();
    }

    public void setTo(UserTo newTo) {
        newTo.setPassword(null);
        userTo = newTo;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}