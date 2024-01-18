package lab01.richard.group04.a2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Map;

public class RegistrationTest {

    private Registration registration;

    @AfterEach
    public void RemoveLastLineFromFile() {    
    try {
            File inputFile = new File("src/main/java/lab01/richard/group04/a2/test_registration.txt");
            File tempFile = new File("src/main/java/lab01/richard/group04/a2/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            String lastLine = null;

            // Read and save all lines except the last one
            while ((currentLine = reader.readLine()) != null) {
                if (lastLine != null) {
                    writer.write(lastLine + System.getProperty("line.separator"));
                }
                lastLine = currentLine;
            }

            // Close readers and writers
            reader.close();
            writer.close();

            // Replace the original file with the temporary file
            inputFile.delete();
            tempFile.renameTo(inputFile);

            System.out.println("Last line removed from the file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    @Test
    public void testRegistrationWithUniqueUsername() {
        // Arrange: Set up the input data
        String username = "testUser";
        String name = "Test User";
        String email = "testuser@example.com";
        String phoneNumber = "1234567890";
        String password = "password123";
        
        // Act: Perform the registration
        // Simulate user input by calling the registration method
        registration.registerUser(name, email, phoneNumber, username, password);

        // Assert: Verify the result
        Map<String, String> userCredentials = registration.loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/registration.txt");

        // Check if the registration was successful (username should exist in the loaded credentials)
        assertTrue(userCredentials.containsKey(username));

        // Verify that the file was modified as expected by loading it and checking if the new registration exists
        Map<String, String> fileContent = registration.loadCredentialsFromFile("src/main/java/lab01/richard/group04/a2/registration.txt");
        assertTrue(fileContent.containsKey(username));
    }
}
