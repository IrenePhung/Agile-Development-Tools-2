package lab01.richard.group04.a2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AddScrollTest {
    
    @Test
    public void testAddScrollUniqueName() {
        boolean result = ScrollManager.addScroll("UniqueScroll", "123", "Scroll Content");
        ScrollManager.removeScroll("UniqueScroll");
        assertTrue(result);
    }

    @Test
    public void testAddScrollNonUniqueName() {
        // Assuming a file with the same name already exists
        ScrollManager.addScroll("NonUniqueName", "496", "Scroll Content");
        boolean result = ScrollManager.addScroll("NonUniqueName", "456", "Scroll Content");
        ScrollManager.removeScroll("NonUniqueName");
        assertFalse(result);
    }

    @Test
    public void testAddScrollUniqueWhiskerId() {
        boolean result = ScrollManager.addScroll("NewScroll", "UniqueWhiskerId", "Scroll Content");
        ScrollManager.removeScroll("NewScroll");
        assertTrue(result);
    }

    @Test
    public void testAddScrollNonUniqueWhiskerId() {
        ScrollManager.addScroll("dumy", "123", "Scroll Content");
        boolean result = ScrollManager.addScroll("AnotherScroll", "123", "Scroll Content");
        ScrollManager.removeScroll("dumy");
        ScrollManager.removeScroll("AnotherScroll");
        assertFalse(result);
    }
}
