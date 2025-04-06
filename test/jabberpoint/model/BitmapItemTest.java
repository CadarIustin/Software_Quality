package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.Font;

/**
 * Unit test for the BitmapItem class
 */
public class BitmapItemTest {
    
    private BitmapItem bitmapItem;
    private Style style;
    
    @BeforeEach
    public void setUp() {
        // Create a style for testing
        style = new Style(10, 20, java.awt.Color.BLACK, "SansSerif", 12, Font.PLAIN);
        
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
        Graphics mockGraphics = mock(Graphics.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        
        // Get the bounding box
        Rectangle box = bitmapItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, style);
        
        // Verify the bounding box is not null and has positive dimensions
        assertNotNull(box);
        assertTrue(box.width >= 0);
        assertTrue(box.height >= 0);
    }
    
    @Test
    public void testDraw() {
        // Create proper mocks for the test
        Graphics2D mockGraphics = mock(Graphics2D.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        
        // Should not throw exception with mocked graphics
        assertDoesNotThrow(() -> bitmapItem.draw(10, 10, 1.0f, mockGraphics, style, mockObserver));
    }
    
    @Test
    public void testInvalidImage() {
        // Test with a non-existent image
        BitmapItem invalidItem = new BitmapItem(1, "NonExistentImage.gif");
        
        // The item should be created but the bufferedImage should be null
        // This is tested indirectly by making sure getBoundingBox and draw don't throw exceptions
        Graphics2D mockGraphics = mock(Graphics2D.class);
        ImageObserver mockObserver = mock(ImageObserver.class);
        
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
