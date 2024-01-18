package lab01.richard.group04.a2;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;



public class Registration {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Registration App!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your Phone Number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        
        registerUser(name, email, phoneNumber, username, password);
        App.main(null);
    }

    public static boolean registerUser(String name, String email, String phoneNumber, String username, String password){
        // Create a formatted string with the registration details
        String registrationInfo = String.format("%s; %s; %s; %s; %s%n",
        name, email, phoneNumber, username, password);
        //loading credentials
        Map<String, String> userCredentials = loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/registration.txt");
        
        if (userCredentials.containsKey(username)) {

            System.out.println("Username already exists. Registration failed.");
        } else{
        // Save the registration details to a file
        String fileName = "src/main/java/lab01/richard/group04/a2/registration.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(registrationInfo);
            System.out.println("Registration Successful");
            
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred while saving the registration details.");
            e.printStackTrace();
            return false;
        }
        
        // scanner.close();
        }
        return false;
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
    
}
