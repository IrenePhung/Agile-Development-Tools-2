package lab01.richard.group04.a2;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.nio.file.*;
import java.io.*;

class Scroll {
    private int id;
    private String name;
    private String uploaderId;
    private String uploadDate;
    private String filePath; 

    public Scroll(int id, String name, String uploaderId, String uploadDate, String filePath) {
        this.id = id;
        this.name = name;
        this.uploaderId = uploaderId;
        this.uploadDate = uploadDate;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getFilePath() {
        return filePath;
    }
}



public class ScrollManager {

    public static boolean addScroll(String filename, String whiskerId, String filecontent) {
        //checking unique name
        if (FileExistsCheck(filename) == true) {
            System.out.println("File with the same name already exists in the folder.");
            return false;
        }

        //checking unique whiskerid
        Map<String, String> scrollCredentials = loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt");
        if (scrollCredentials.containsKey(whiskerId)) {
            System.out.println("Whisker ID is not unique");
            return false;
        }

        //upload date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        String uploadDate = formattedDate;

        //adding Scroll
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/lab01/richard/group04/a2/Scrolls/" + filename + ".txt");
            // Write the content to the file
            fileWriter.write(filecontent);
            // Close the FileWriter to ensure changes are saved
            fileWriter.close();
            System.out.println("Scroll added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while writing to the file.");
        }


        //updating scroll information file
        String scrollInfo = String.format("%s; %s; %s\n", filename, whiskerId, uploadDate);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt", true))) {
            writer.write(scrollInfo);
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred while saving the registration details.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean viewScroll() {
        System.out.println("View Scrolls:");
        System.out.println("ID\tName\tUpload Date");

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 3) {
                    String filename = parts[0];
                    String whiskerId = parts[1];
                    String uploadDate = parts[2];
                    System.out.println(whiskerId + "\t" + filename + "\t" + uploadDate);
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading credentials.");
            e.printStackTrace();
            return false;
        }
        return false;

    }

    public static boolean removeScroll(String filename) {
        //remove file details from Scroll_dataikl.txt file
        RemoveLineFromFile(filename);
        //remove file from the folder
        String folderPath = "src/main/java/lab01/richard/group04/a2/Scrolls"; // Specify the path to the folder
        String fileNameToRemove = filename + ".txt"; // Specify the name of the file to remove
        File folder = new File(folderPath);
        // Check if the folder exists
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("The folder does not exist.");
            return false;
        }
        // List the files in the folder
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equals(fileNameToRemove)) {
                    // Found the file with the given name
                    if (file.delete()) {
                        System.out.println("File '" + fileNameToRemove + "' has been removed.");
                        return true;
                    } else {
                        System.out.println("Failed to remove the file '" + fileNameToRemove + "'.");
                        return false;
                    }

                }
            }
            // File not found in the folder
            System.out.println("File '" + fileNameToRemove + "' not found in the folder.");
            return false;
        } else {
            System.out.println("An error occurred while listing files in the folder.");
            return false;
        }
    }

    public static boolean previewScroll(String filename) {
        String filePath = "src/main/java/lab01/richard/group04/a2/Scrolls/" + filename + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Name: " + filename);
            System.out.println("Scroll Preview:");
            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null && lineCount < 5) {
                System.out.println(line);
                lineCount++;
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean downloadScroll(String filename) {
        String sourcePath = "src/main/java/lab01/richard/group04/a2/Scrolls/" + filename + ".txt";
        String destinationPath = "src/main/java/lab01/richard/group04/a2/Downloads/" + filename + ".txt";

        try {
            // Create the source and destination paths
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);

            // Use the Files.copy method to copy the file
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            updateDownloadCount(filename);

            System.out.println("File copied successfully.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void main(String[] args) {
        ScrollManager scrollManager = new ScrollManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Virtual Scroll Access System (VSAS)");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("Press M to return to the menu at whichever time!");
            System.out.println("1. View Scrolls");
            System.out.println("2. Preview Scrolls");
            System.out.println("3. Edit/Update Scrolls");
            System.out.println("4. Add Scrolls");
            System.out.println("5. Download Scrolls");
            System.out.println("6. Remove Scrolls");
            System.out.println("7. Search Scrolls by filter");
            System.out.println("8. Manage User Profile");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //view scrolls
                    scrollManager.viewScroll();
                    break;
                case 2:
                    //preview scrolls
                    System.out.print("Enter the scroll name to preview: ");
                    String name = scanner.next();
                    scrollManager.previewScroll(name);
                    break;

                case 3:
                    //Edit Update scrolls
                    editUpdate();
                    break;


                case 4:
                    //add scrolls
                    System.out.print("Enter the scroll name: ");
                    String filename = scanner.next();
                    System.out.print("Enter your Whisker ID: ");
                    String whiskerId = scanner.next();
                    System.out.print("Upload the Binary File: ");
                    String filecontent = scanner.next();
                    scrollManager.addScroll(filename, whiskerId, filecontent);
                    break;
                case 5:
                    System.out.print("Enter the scroll name to download: ");
                    String downloadFileName = scanner.next();
                    scrollManager.downloadScroll(downloadFileName);
                    break;
                case 6:
                    System.out.print("Enter the scroll name to remove: ");
                    String fileName = scanner.next();
                    scrollManager.removeScroll(fileName);
                    break;
                case 7:
                    //Filter scrolls;
                    scrollManager.filterScrolls();
                    break;

                case 8:
                    ProfileManagement profileManagement = new ProfileManagement();
                    profileManagement.editUserInformation();
                    break;
                case 9:
                    System.out.println("Thank you for using VSAS. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static Map<String, String> loadCredentialsFromFile(String fileName) {
        Map<String, String> credentials = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 3) {
                    String filename = parts[0];
                    String whiskerId = parts[1];
                    String uploadDate = parts[2];

                    credentials.put(whiskerId, filename);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading credentials.");
            e.printStackTrace();
        }
        return credentials;
    }

    public static boolean FileExistsCheck(String fileName) {
        String folderPath = "src/main/java/lab01/richard/group04/a2/Scrolls";
        // String fileName = "example.txt"; // Replace with the desired file name.

        Path folder = Paths.get(folderPath);
        Path filePath = folder.resolve(fileName + ".txt");

        if (Files.exists(filePath)) {
            return true;
        } else {
            return false;
        }

    }


    public static void RemoveLineFromFile(String fileName) {

        // Input and temporary file paths
        String inputFilePath = "src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt";
        String tempFilePath = "src/main/java/lab01/richard/group04/a2/Scrolls/temp.txt";

        try {
            // Open the input file for reading
            BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

            // Create a temporary file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // Check if the line starts with the unique characters
                if (!currentLine.startsWith(fileName)) {
                    // If it doesn't start with the unique prefix, write it to the temporary file
                    writer.write(currentLine);
                    writer.newLine();
                }
            }

            // Close the input and temporary files
            reader.close();
            writer.close();

            // Delete the original file
            File inputFile = new File(inputFilePath);
            inputFile.delete();

            // Rename the temporary file to the original file name
            File tempFile = new File(tempFilePath);
            tempFile.renameTo(inputFile);

            System.out.println("Specific line removed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Update
    public static String loggedInUser = null;

    public static boolean editUpdate() {
        Scanner sc = new Scanner(System.in);

        System.out.println("What do you want to edit?");
        System.out.println("1. Scroll Name");
        System.out.println("2. Whisker ID");
        System.out.println("3. Binary File");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.println("Edit Scroll Name or Binary File: Enter the name's scroll you wish to edit.");
        System.out.println("Edit Whisker ID: Enter the Whisker ID's scroll you wish to edit.");
        System.out.println("Enter the detail corresponding to the scroll you wish to edit:");
        String userInput = sc.nextLine();

        final Path SCROLLS_PATH = Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(SCROLLS_PATH);
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return false;
        }

        int index = -1;
        String[] currentDetails = null;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] details = line.split(";");

            if (details.length < 3) continue;

            if ((choice == 1 && details[0].trim().equals(userInput)) ||
                    (choice == 2 && details[1].trim().equals(userInput)) ||
                    (choice == 3 && details[0].trim().equals(userInput))) {
                index = i;
                currentDetails = details;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Detail not found.");
            return false;
        }

        String oldFileName = currentDetails[0] + ".txt";

        switch (choice) {
            case 1:
                System.out.println("Enter new scroll name:");
                String newName = sc.nextLine();
                for (String line : lines) {
                    if (line.startsWith(newName + ";")) {
                        System.out.println("A scroll with this name already exists.");
                        return false;
                    }
                }

                writeToLog(loggedInUser, oldFileName, "scroll name from " + currentDetails[0] + " to " + newName);


                currentDetails[0] = newName;
                currentDetails[2] = getCurrentDate();
                try {
                    Files.move(Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/" + oldFileName),
                            Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/" + newName + ".txt"),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Error renaming the file.");
                    return false;
                }
                break;

            case 2:
                System.out.println("Enter the new Whisker ID:");
                String newWhiskerID = sc.nextLine();
                boolean whiskerIDExists = lines.stream().anyMatch(line -> line.split(";")[1].trim().equals(newWhiskerID));

                if (whiskerIDExists) {
                    System.out.println("This Whisker ID is not unique.");
                    return false;
                } else {
                    writeToLog(loggedInUser, oldFileName, "updated a scroll details: Whisker ID from " + currentDetails[1] + " to " + newWhiskerID);
                    currentDetails[1] = newWhiskerID;
                    currentDetails[2] = getCurrentDate();
                }
                break;

            case 3:
                System.out.println("Enter new binary file content:");
                String newContent = sc.nextLine();

                String fileName = currentDetails[0] + ".txt";
                Path baseDirectory = Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls");
                Path filePath = baseDirectory.resolve(fileName);

                if (!Files.exists(filePath)) {
                    System.out.println("File " + fileName + " not found.");
                    return false;
                }

                currentDetails[2] = getCurrentDate();
                try {
                    Files.write(filePath, newContent.getBytes());
                    writeToLog(loggedInUser, oldFileName, "updated the binary file content for scroll " + currentDetails[0] + " to " + newContent);

                } catch (IOException e) {
                    System.out.println("Error writing to the file.");
                    return false;
                }
                break;
        }

        lines.set(index, String.join(";", currentDetails));
        try {
            Files.write(Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt"), lines);
        } catch (IOException e) {
            System.out.println("Error writing to the file.");
            return false;
        }

        System.out.println("Scroll details updated successfully!");
        return true;
    }

    //Log File
    private static String getCurrentDate() {
        return LocalDate.now().toString();
    }

    private static void writeToLog(String username, String filename, String changeDetails) {
        Path logPath = Paths.get("src/main/java/lab01/richard/group04/a2/Scrolls/Log.txt");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = timestamp + " \"" + username + "\" uploaded \"" + filename + "\": " + changeDetails + "\n";

        try {
            Files.write(logPath, logMessage.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error writing to the log file.");
        }
    }

    private static String getUsernameFromRegistration(String username) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("src/main/java/lab01/richard/group04/a2/ProfileManagement/registration.txt"));
        } catch (IOException e) {
            System.out.println("Error reading registration file.");
            return null;
        }

        for (String line : lines) {
            String[] details = line.split(";");
            if (details[3].trim().equals(username)) {
                return details[3].trim();
            }
        }
        return null;
    }



    //Search

        public static String SCROLLS_DETAIL_PATH = "src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt";

        public static void filterScrolls() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Select an option to filter by:");
        System.out.println("1. Uploader Username");
        System.out.println("2. First letter of the scroll ID");
        System.out.println("3. First letter of the scroll name");
        System.out.println("4. Upload date");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String filterCriteria = "";

        switch (choice) {
            case 1:
                System.out.print("Enter username: ");
                filterCriteria = scanner.nextLine().toLowerCase(); // Assuming usernames are case-insensitive
                break;
            case 2:
                System.out.print("Enter first letter of the scroll ID: ");
                filterCriteria = scanner.nextLine().toLowerCase();
                break;
            case 3:
                System.out.print("Enter first letter of the scroll name: ");
                filterCriteria = scanner.nextLine().toLowerCase();
                break;
            case 4:
                System.out.print("Enter upload date (yyyy-MM-dd format): ");
                filterCriteria = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

            try (BufferedReader reader = new BufferedReader(new FileReader(SCROLLS_DETAIL_PATH));
                 BufferedReader logReader = new BufferedReader(new FileReader("src/main/java/lab01/richard/group04/a2/Scrolls/Log.txt"))) {

                boolean found = false;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String filename = parts[0].trim();
                    String whiskerId = parts[1].trim();
                    String date = parts[2].trim();

                    switch (choice) {
                        case 1:  // Uploader Username
                            String logLine = logReader.readLine();
                            if (logLine != null && logLine.contains("\"" + filterCriteria + "\" uploaded")) {
                                System.out.println("Filename: " + filename + "\tWhisker ID: " + whiskerId + "\tDate: " + date);
                                found = true;
                            }
                            break;
                        case 2:  // First letter of the scroll ID
                            if (whiskerId.toLowerCase().startsWith(filterCriteria.toLowerCase())) {
                                System.out.println("Filename: " + filename + "\tWhisker ID: " + whiskerId + "\tDate: " + date);
                                found = true;
                            }
                            break;
                        case 3:  // First letter of the scroll name
                            if (filename.toLowerCase().startsWith(filterCriteria.toLowerCase())) {
                                System.out.println("Filename: " + filename + "\tWhisker ID: " + whiskerId + "\tDate: " + date);
                                found = true;
                            }
                            break;
                        case 4:  // Upload date
                            if (date.equals(filterCriteria)) {
                                System.out.println("Filename: " + filename + "\tWhisker ID: " + whiskerId + "\tDate: " + date);
                                found = true;
                            }
                            break;
                    }
                }
            }

            if (!found) {
                switch (choice) {
                    case 1:
                        System.out.println("No scrolls uploaded by user: " + filterCriteria);
                        break;
                    case 4:
                        System.out.println("No scrolls uploaded on date: " + filterCriteria);
                        break;
                    default:
                        System.out.println("No scrolls found for the given criteria.");
                        break;
                }
            }
            } catch (IOException e) {
                System.err.println("An error occurred while filtering scrolls.");
                e.printStackTrace();
            }
        }

    public static void updateDownloadCount(String filename) {
        String statsPath = "src/main/java/lab01/richard/group04/a2/downloads/ScrollStats.txt";
        Map<String, Integer> stats = new HashMap<>();

        // Load current statistics
        try (BufferedReader reader = new BufferedReader(new FileReader(statsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("; ");
                if (parts.length == 2) {
                    stats.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the count for the given filename
        stats.put(filename, stats.getOrDefault(filename, 0) + 1);

        // Save the updated statistics
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(statsPath))) {
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                writer.write(entry.getKey() + "; " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}




