package ru.votesystem.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.votesystem.model.User;

public class UsersUtil {
    public static User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
