package lab01.richard.group04.a2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserLogin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> userCredentials = loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/registration.txt");

        System.out.println("\nWelcome to the Login App!");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (userCredentials.containsKey("admin") && userCredentials.get("admin").equals(password)) {
            System.out.println("\nLogin successful!");
            Admin.main(null);
        } else if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            System.out.println("\nLogin successful!");
            ScrollManager.loggedInUser = username;
            ScrollManager.main(null);
        } else {
            System.out.println("Login failed. Invalid username or password.\n");
            App.main(null);
        }


        scanner.close();
    }

    public static Map<String, String> loadCredentialsFromFile(String fileName) {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 5) {
                    String username = parts[3];
                    String password = parts[4];
                    credentials.put(username, password);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading credentials.");
            e.printStackTrace();
        }
        return credentials;
    }

    // Inside UserLogin class

    public boolean authenticate(String username, String password, String filePath) {
        Map<String, String> userCredentials = loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/registration.txt");
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }


}