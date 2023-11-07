import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private static List<User> users;
    public String username;
    public String password;
    public boolean isAdmin;

    public static List<User> getUsers() {
        if (users != null) return users;

        try {
            File file = new File("users.json");
            return users = Main.mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            return users = new ArrayList<>();
        }
    }

    public static void saveUsers() {
        if (users == null) throw new RuntimeException("Tried saving, but there are no users to save.");

        try {
            File file = new File("users.json");
            Main.mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(file, users);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save users.");
        }
    }

    public static User getUser(String username, String password) {
        List<User> users = User.getUsers();

        for (User user : users) {
            if (user.username.equalsIgnoreCase(username) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }

    public static User getUser(String username) {
        List<User> users = User.getUsers();

        for (User user : users) {
            if (user.username.equalsIgnoreCase(username)) {
                return user;
            }
        }

        return null;
    }
}
