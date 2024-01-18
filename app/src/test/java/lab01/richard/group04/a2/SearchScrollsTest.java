package lab01.richard.group04.a2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static lab01.richard.group04.a2.ScrollManager.filterScrolls;
import static org.junit.jupiter.api.Assertions.*;

public class SearchScrollsTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        ScrollManager.SCROLLS_DETAIL_PATH = "src/main/java/lab01/richard/group04/a2/Scrolls/Scrolls_details_test.txt"; }
    @Test
    public void testFilterByScrollID() {
        String input = "2\nI\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        filterScrolls();
        assertTrue(outContent.toString().contains("ID1") && outContent.toString().contains("ID2"));
    }

    @Test
    public void testFilterByScrollName() {
        String input = "3\nf\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        filterScrolls();
        assertTrue(outContent.toString().contains("filename1") && outContent.toString().contains("filename2"));
    }

    @Test
    public void testFilterByUploadDate() {
        String input = "4\n2023-10-23\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        filterScrolls();
        assertTrue(outContent.toString().contains("2023-10-23"));
    }

    @Test
    public void testInvalidOption() {
        String input = "5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        filterScrolls();
        assertTrue(outContent.toString().contains("Invalid option."));
    }
}
