package lab01.richard.group04.a2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Admin {

    private static final String FILE_PATH = "src/main/java/lab01/richard/group04/a2/registration.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View all users");
            System.out.println("2. Add a user");
            System.out.println("3. Delete a user");
            System.out.println("4. View download statistics");
            System.out.println("5. View update statistics");
            System.out.println("6. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine();  // consume the newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    System.out.println("Enter user's name: ");
                    String name = scanner.nextLine();

                    System.out.println("Enter user's email: ");
                    String email = scanner.nextLine();

                    System.out.println("Enter user's phone: ");
                    String phone = scanner.nextLine();

                    System.out.println("Enter user's username: ");
                    String username = scanner.nextLine();

                    System.out.println("Enter user's password: ");
                    String password = scanner.nextLine();

                    addUser(name, email, phone, username, password);
                    break;
                case 3:
                    System.out.println("Enter the username of the user you want to delete: ");
                    String deleteUser = scanner.nextLine();
                    deleteUser(deleteUser);
                    break;
                case 4:
                    viewScrollStatistics();
                    break;
                case 5:
                    viewUploadStatsFromLog();
                    break;
                case 6:
                    System.out.println("Logging out and exiting the program. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public static void viewAllUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
    }

    public static void addUser(String name, String email, String phone, String username, String password) {
        // Assuming the Registration.registerUser method is public
        Registration.registerUser(name, email, phone, username, password);
    }

    public static void deleteUser(String username) {
        Map<String, String> credentials = UserLogin.loadCredentialsFromFile(FILE_PATH);
        if (!credentials.containsKey(username)) {
            System.out.println("User not found!");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.contains(username)) { // a very naive check. You may want a more robust solution.
                    output.append(line).append("\n");
                }
            }
            reader.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            writer.write(output.toString());
            writer.close();

            System.out.println("User deleted successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the user.");
            e.printStackTrace();
        }
    }
    public static void viewScrollStatistics() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab01/richard/group04/a2/Downloads/ScrollStats.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the scroll statistics.");
            e.printStackTrace();
        }
    }



    public static void viewUploadStatsFromLog() {
        Path logPath = Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/log.txt");
        Map<String, Integer> uploadCounts = new HashMap<>();

        try {
            List<String> logLines = Files.readAllLines(logPath);
            for (String logLine : logLines) {
                if (logLine.contains("uploaded")) {
                    int startIdx = logLine.indexOf('"') + 1;
                    int endIdx = logLine.indexOf(".txt");
                    String scrollName = logLine.substring(startIdx, endIdx);

                    uploadCounts.put(scrollName, uploadCounts.getOrDefault(scrollName, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the log.txt file.");
            e.printStackTrace();
            return;
        }

        System.out.println("Scroll Name \t Upload Count");
        for (Map.Entry<String, Integer> entry : uploadCounts.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }


}
