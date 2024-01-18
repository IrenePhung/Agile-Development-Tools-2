package lab01.richard.group04.a2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PreviewScrollTest {
    
    @Test
    public void testPreviewScrollFileNotFound() {
        boolean result = ScrollManager.previewScroll("UniqueScroll");
        assertFalse(result);
    }

    @Test
    public void testPreviewScrollFileFound() {
        ScrollManager.addScroll("UniqueScroll", "123", "Scroll Content");
        boolean result = ScrollManager.previewScroll("UniqueScroll");
        ScrollManager.removeScroll("UniqueScroll");
        assertTrue(result);
    }

}