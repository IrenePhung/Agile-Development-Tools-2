package lab01.richard.group04.a2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewScrollTest {
    private static final String ORIGINAL_CONTENT_PATH = "src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details.txt";
    private static final String TEST_FILE_PATH = "src/test/java/lab01/richard/group04/a2/ViewScroll_test.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // Backup original file content
        Files.copy(Paths.get(TEST_FILE_PATH), Paths.get(ORIGINAL_CONTENT_PATH), StandardCopyOption.REPLACE_EXISTING);

        // Setup test data
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH));
        writer.write("filename1; ID1; 2023-10-23\n");
        writer.write("filename2; ID2; 2023-10-23\n");

        writer.close();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Restore original file content
        Files.copy(Paths.get(ORIGINAL_CONTENT_PATH), Paths.get(TEST_FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
    }





    @Test
    public void testViewScroll_InvalidData() throws IOException {
        // Setup test data with invalid format
        BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH));
        writer.write("filename1;ID1\n");
        writer.write("filename2;2023-10-23\n");
        writer.close();

        assertFalse(ScrollManager.viewScroll());
    }

    @Test
    public void testViewScroll_FileDoesNotExist() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }

        assertFalse(ScrollManager.viewScroll());
    }

}
