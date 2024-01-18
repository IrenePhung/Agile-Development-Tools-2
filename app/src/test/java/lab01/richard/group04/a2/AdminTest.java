package lab01.richard.group04.a2;


import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    private static final String TEST_FILE_PATH = "src/main/java/lab01/richard/group04/a2/registration_test.txt";
    private static final String ORIGINAL_CONTENT = "John;john@example.com;123456789;john;password123\n" +
            "Alice;alice@example.com;987654321;alice;password456\n";

    @BeforeEach
    public void setup() throws IOException {
        // Set up the test file with known content before each test
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH));
        writer.write(ORIGINAL_CONTENT);
        writer.close();
    }

    @Test
    public void testDeleteUser_UserExists() {
        Admin.deleteUser("mark");
        assertFalse(isUserInFile("mark"), "The user should have been deleted from the file.");
    }

    @Test
    public void testDeleteUser_UserDoesNotExist() {
        Admin.deleteUser("bam");
        assertTrue(isUserInFile("Alice"), "Unrelated user Alice should still be in the file.");
    }



    private boolean isUserInFile(String username) {
        try (Scanner scanner = new Scanner(new File(TEST_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
