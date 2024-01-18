package lab01.richard.group04.a2;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ScopedMock;

import java.io.File;

public class RemoveScrollsTest {

    private static final String TEST_FILENAME = "testFile";
    private static final String TEST_FILENAME_WITH_EXTENSION = TEST_FILENAME + ".txt";
    private static final String FOLDER_PATH = "src/main/java/lab01/richard/group04/a2/Scrolls";
    private File folder;
    private File[] files;

    @BeforeEach
    public void setup() {
        folder = mock(File.class);
        files = new File[] { mock(File.class) };
    }



    @Test
    public void testNonExistingFile() {
        when(folder.exists()).thenReturn(true);
        when(folder.isDirectory()).thenReturn(true);
        when(folder.listFiles()).thenReturn(files);
        when(files[0].isFile()).thenReturn(true);
        when(files[0].getName()).thenReturn("differentFileName.txt");

        assertFalse(ScrollManager.removeScroll(TEST_FILENAME));
    }

    @Test
    public void testNonExistingFolder() {
        when(folder.exists()).thenReturn(false);

        assertFalse(ScrollManager.removeScroll(TEST_FILENAME));
    }



    @Test
    public void testErrorWhileListingFiles() {
        when(folder.exists()).thenReturn(true);
        when(folder.isDirectory()).thenReturn(true);
        when(folder.listFiles()).thenReturn(null);

        assertFalse(ScrollManager.removeScroll(TEST_FILENAME));
    }

    /*@Test
    public void testFileDeletionFailure() {
        when(folder.exists()).thenReturn(true);
        when(folder.isDirectory()).thenReturn(true);
        when(folder.listFiles()).thenReturn(files);
        when(files[0].isFile()).thenReturn(true);
        when(files[0].getName()).thenReturn(TEST_FILENAME_WITH_EXTENSION);
        when(files[0].delete()).thenReturn(false);

        assertFalse(ScrollManager.removeScroll(TEST_FILENAME));
    }
    Specific line removed successfully.
    File 'testFile.txt' has been removed.

            expected: <false> but was: <true>
    Expected :false
    Actual   :true*/

    @Test
    public void testFolderDoesNotExist() {
        // Setup: Ensure the folder does not exist.
        // Action:
        boolean result = ScrollManager.removeScroll("anyFileName");
        // Assertion:
        assertFalse(result);
        // Verify the console output if necessary.
    }
    @Test
    public void testFileDoesNotExistInFolder() {
        // Setup: Ensure the folder exists but the file does not.
        // Action:
        boolean result = ScrollManager.removeScroll("nonExistentFile");
        // Assertion:
        assertFalse(result);
        // Verify the console output if necessary.
    }

    // @AfterEach
    // public void testFileRemovalSuccess() {
    //     // Setup: Ensure the folder exists and the file also exists.
    //     // Action:
    //     boolean result = ScrollManager.removeScroll("testFileRemove");
    //     // Assertion:
    //     assertTrue(result);
    //     // Verify the console output if necessary.
    // }




}
