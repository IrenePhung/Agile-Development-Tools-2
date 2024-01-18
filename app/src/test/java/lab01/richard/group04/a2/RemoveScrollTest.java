package lab01.richard.group04.a2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveScrollTest {
    
    @Test
    public void testRemoveScrollFileNotFound() {
        boolean result = ScrollManager.removeScroll("UniqueScroll");
        assertFalse(result);
    }

    @Test
    public void testRemoveScrollFileFound() {
        ScrollManager.addScroll("UniqueScroll", "123", "Scroll Content");
        boolean result = ScrollManager.removeScroll("UniqueScroll");
        assertTrue(result);
    }

}