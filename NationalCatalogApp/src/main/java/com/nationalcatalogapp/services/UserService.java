package com.nationalcatalogapp.services;

import com.nationalcatalogapp.models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private List<User> users = new ArrayList<>();

    // Метод для регистрации нового пользователя
    public void registerUser(String username, String password, String role) {
        Optional<User> existingUser = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        if (existingUser.isPresent()) {
            System.out.println("User with username " + username + " already exists.");
        } else {
            User newUser = new User(username, password, role);
            users.add(newUser);
            System.out.println("User registered: " + newUser);
        }
    }

    // Метод для авторизации пользователя
    public boolean loginUser(String username, String password) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
        if (user.isPresent()) {
            System.out.println("Login successful for user: " + username);
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Метод для удаления пользователя по его имени
    public void deleteUser(String username) {
        Optional<User> userToDelete = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        if (userToDelete.isPresent()) {
            users.remove(userToDelete.get());
            System.out.println("User deleted: " + userToDelete.get());
        } else {
            System.out.println("User with username " + username + " not found.");
        }
    }

    // Метод для получения всех пользователей
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
