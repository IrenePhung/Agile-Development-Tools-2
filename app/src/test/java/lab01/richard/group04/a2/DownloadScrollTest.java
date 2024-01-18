package lab01.richard.group04.a2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DownloadScrollTest {
    
    @Test
    public void testDownloadScrollFileNotFound() {
        boolean result = ScrollManager.downloadScroll("UniqueScroll");
        assertFalse(result);
    }

    @Test
    public void testRemoveScrollFileFound() {
        ScrollManager.addScroll("testScroll", "123", "Scroll Content");
        boolean result = ScrollManager.downloadScroll("testScroll");
        ScrollManager.removeScroll("testScroll");
        assertTrue(result);
    }

}