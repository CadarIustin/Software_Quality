package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * Unit test for the BitmapItem class
 */
public class BitmapItemTest {
    
    private BitmapItem bitmapItem;
    private Style style;
    
    @BeforeEach
    public void setUp() {
        // Create a style for testing
        style = new Style(1, 12, java.awt.Color.BLACK, "Arial", 10, 0);
        
        // Create a BitmapItem with the JabberPoint.gif image
        bitmapItem = new BitmapItem(1, "JabberPoint.gif");
    }
    
    @Test
    public void testConstructor() {
        // Test the constructor with a valid image
        BitmapItem item = new BitmapItem(2, "JabberPoint.gif");
        assertEquals(2, item.getLevel());
        assertEquals("img/JabberPoint.gif", item.getName());
        
        // Test the default constructor
        BitmapItem defaultItem = new BitmapItem();
        assertEquals(0, defaultItem.getLevel());
        assertNull(defaultItem.getName());
    }
    
    @Test
    public void testGetName() {
        assertEquals("img/JabberPoint.gif", bitmapItem.getName());
    }
    
    @Test
    public void testGetBoundingBox() {
        // Create mock objects
        Graphics mockGraphics = null; // Not actually used in the method
        ImageObserver mockObserver = null; // Not actually used in the method
        
        // Get the bounding box
        Rectangle box = bitmapItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, style);
        
        // Verify the bounding box is not null and has positive dimensions
        assertNotNull(box);
        assertTrue(box.width >= 0);
        assertTrue(box.height >= 0);
    }
    
    @Test
    public void testDraw() {
        // This is a visual test that's hard to verify without mocking
        // We'll just ensure it doesn't throw exceptions
        Graphics mockGraphics = null; // We're not actually using this
        ImageObserver mockObserver = null; // We're not actually using this
        
        // Should not throw exception even with null graphics
        assertDoesNotThrow(() -> bitmapItem.draw(10, 10, 1.0f, mockGraphics, style, mockObserver));
    }
    
    @Test
    public void testInvalidImage() {
        // Test with a non-existent image
        BitmapItem invalidItem = new BitmapItem(1, "NonExistentImage.gif");
        
        // The item should be created but the bufferedImage should be null
        // This is tested indirectly by making sure getBoundingBox and draw don't throw exceptions
        Graphics mockGraphics = null;
        ImageObserver mockObserver = null;
        
        // Should not throw exception for invalid image
        assertDoesNotThrow(() -> invalidItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, style));
        assertDoesNotThrow(() -> invalidItem.draw(10, 10, 1.0f, mockGraphics, style, mockObserver));
    }
    
    @Test
    public void testToString() {
        // Test that toString returns a non-empty string
        String result = bitmapItem.toString();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
