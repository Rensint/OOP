package Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String username;
    private String password;

    // List to store users
    private static ArrayList<User> userList = new ArrayList<>();

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Login and Logout
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("User " + username + " logged out.");
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    public String toString() {
        return "Username: " + username;
    }

    // CRUD Operations for User
    
    // Add new user
    public static void addUser(Scanner scanner) {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        userList.add(newUser);
        System.out.println("User added successfully!");
    }

    // View all users
    public static void viewUsers() {
        if (userList.isEmpty()) {
            System.out.println("No users available.");
        } else {
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    // Update user details
    public static void updateUser(Scanner scanner) {
        System.out.print("Enter the Username to update: ");
        String username = scanner.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                System.out.print("Enter new Password: ");
                String newPassword = scanner.nextLine();
                user.setPassword(newPassword);
                System.out.println("User updated successfully!");
                return;
            }
        }
        System.out.println("User with Username " + username + " not found.");
    }

    // Delete user
    public static void deleteUser(Scanner scanner) {
        System.out.print("Enter the Username to delete: ");
        String username = scanner.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                userList.remove(user);
                System.out.println("User deleted successfully!");
                return;
            }
        }
        System.out.println("User with Username " + username + " not found.");
    }
}

