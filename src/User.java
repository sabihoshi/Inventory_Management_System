import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class User {
    private static LinkedList<User> users;
    public String username;
    public String password;
    public boolean isAdmin;

    /**
     * Retrieves the list of users from a JSON file.
     * If the list of users has already been loaded, it returns the cached value.
     * If the file cannot be read, an empty list is returned.
     *
     * @return The list of users.
     */
    public static LinkedList<User> getUsers() {
        if (users != null) return users;

        try {
            File file = new File("users.json");
            return users = Main.mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            return users = new LinkedList<>();
        }
    }

    /**
     * Saves the list of users to a JSON file.
     *
     * @throws RuntimeException if there are no users to save or if the users file cannot be written.
     */
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

    /**
     * Retrieves a user based on the provided username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The user object if found, else null.
     */
    public static User getUser(String username, String password) {
        LinkedList<User> users = User.getUsers();

        for (User user : users) {
            if (user.username.equalsIgnoreCase(username) && user.password.equals(password)) {
                return user;
            }
        }

        return null;
    }
}
