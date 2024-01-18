package lab01.richard.group04.a2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfileManagement {

    private List<UserInfo> mockUserData;


    public void setMockUserData(List<UserInfo> mockData) {
        this.mockUserData = mockData;
    }

    public void editUserInformation() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you wish to edit your username or password?");
        System.out.print("Enter 'username' or 'password': ");
        String fieldToEdit = scanner.nextLine();

        // Load user data from the registration file
        List<UserInfo> userData = loadUserDataFromFile();

        System.out.print("Enter your current username: ");
        String currentUsername = scanner.nextLine();

        // Check if the entered username exists
        int index = findUserIndex(currentUsername, userData);

        if (index != -1) {
            if (fieldToEdit.equals("username")) {
                System.out.print("Enter your new username: ");
                String newUsername = scanner.nextLine();

                // Update the username in memory
                userData.get(index).setUsername(newUsername);

                // Save the updated user information to the registration file
                saveUserDataToFile("src/main/java/lab01/richard/group04/a2/registration.txt", userData);

                System.out.println("Username updated successfully.");
            } else if (fieldToEdit.equals("password")) {
                // Check if the current password is correct
                boolean passwordCorrect = false;
                while (!passwordCorrect) {
                    System.out.print("Enter your current password: ");
                    String currentPassword = scanner.nextLine();

                    // Check if the entered current password matches the stored password
                    if (userData.get(index).getPassword().equals(currentPassword)) {
                        System.out.print("Enter your new password: ");
                        String newPassword = scanner.nextLine();

                        // Update the password in memory
                        userData.get(index).setPassword(newPassword);

                        // Save the updated user information to the registration file
                        saveUserDataToFile("src/main/java/lab01/richard/group04/a2/registration.txt", userData);

                        System.out.println("Password updated successfully.");
                        passwordCorrect = true;
                    } else {
                        System.out.println("Incorrect current password. Please try again.");
                    }
                }
            } else {
                System.out.println("Invalid field. User information update failed.");
            }
        } else {
            System.out.println("Username not found. User information update failed.");
        }
    }

    public List<UserInfo> loadUserDataFromFile() { //
        List<UserInfo> userData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab01/richard/group04/a2/registration.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 5) {
                    String name = parts[0];
                    String email = parts[1];
                    String phoneNumber = parts[2];
                    String username = parts[3];
                    String password = parts[4];
                    userData.add(new UserInfo(name, email, phoneNumber, username, password));
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading user data.");
            e.printStackTrace();
        }
        return userData;
    }

    public void saveUserDataToFile(String fileName, List<UserInfo> userData) { //
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (UserInfo user : userData) {
                writer.write(user.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("An error occurred while saving user data.");
            e.printStackTrace();
        }
    }

    public int findUserIndex(String username, List<UserInfo> userData) { //
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}

class UserInfo {
    private String name;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;

    public UserInfo(String name, String email, String phoneNumber, String username, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    // Implement getters and setters for other fields as needed

    @Override
    public String toString() {
        return name + "; " + email + "; " + phoneNumber + "; " + username + "; " + password;
    }


}