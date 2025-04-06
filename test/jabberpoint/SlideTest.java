package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.ImageObserver;
import java.util.List;

import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.Style;

/**
 * Unit test for the Slide class
 */
public class SlideTest {
    
    private Slide slide;
    
    @BeforeEach
    public void setUp() {
        slide = new Slide();
        slide.setTitle("Test Slide");
        // Ensure styles are initialized
        Style.createStyles();
    }
    
    @Test
    public void testGetTitle() {
        assertEquals("Test Slide", slide.getTitle());
    }
    
    @Test
    public void testSetTitle() {
        slide.setTitle("New Title");
        assertEquals("New Title", slide.getTitle());
    }
    
    @Test
    public void testAppendItem() {
        TextItem item = new TextItem(1, "Test Item");
        slide.append(item);
        
        // Get the items and check if our item is there
        assertEquals(1, slide.getSize());
        assertEquals(item, slide.getSlideItem(0));
    }
    
    @Test
    public void testAppendMultipleItems() {
        TextItem item1 = new TextItem(1, "Item 1");
        TextItem item2 = new TextItem(2, "Item 2");
        TextItem item3 = new TextItem(3, "Item 3");
        
        slide.append(item1);
        slide.append(item2);
        slide.append(item3);
        
        assertEquals(3, slide.getSize());
        assertEquals(item1, slide.getSlideItem(0));
        assertEquals(item2, slide.getSlideItem(1));
        assertEquals(item3, slide.getSlideItem(2));
    }
    
    @Test
    public void testAppendByLevelAndMessage() {
        slide.append(2, "Test Message");
        
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals(2, item.getLevel());
        assertEquals("Test Message", ((TextItem) item).getText());
    }
    
    @Test
    public void testGetSlideItems() {
        // Initially, the slide should have no items
        List<SlideItem> items = slide.getSlideItems();
        assertNotNull(items);
        assertEquals(0, items.size());
        
        // Add an item and check again
        TextItem item = new TextItem(1, "Test Item");
        slide.append(item);
        items = slide.getSlideItems();
        assertEquals(1, items.size());
    }
    
    @Test
    public void testGetSize() {
        assertEquals(0, slide.getSize());
        
        slide.append(new TextItem(1, "Item 1"));
        assertEquals(1, slide.getSize());
        
        slide.append(new TextItem(2, "Item 2"));
        assertEquals(2, slide.getSize());
    }
    
    @Test
    public void testDraw() {
        // Create a mock Graphics2D object
        Graphics2D mockGraphics = mock(Graphics2D.class);
        Rectangle area = new Rectangle(0, 0, 800, 600);
        ImageObserver mockObserver = mock(ImageObserver.class);
        
        // Mock necessary methods to prevent NullPointerException
        when(mockGraphics.getFont()).thenReturn(new Font("SansSerif", Font.PLAIN, 12));
        when(mockGraphics.getColor()).thenReturn(Color.BLACK);
        
        // Should not throw exception with mocked graphics
        assertDoesNotThrow(() -> slide.draw(mockGraphics, area, mockObserver));
        
        // Add an item and test again
        slide.append(new TextItem(1, "Test Item"));
        assertDoesNotThrow(() -> slide.draw(mockGraphics, area, mockObserver));
        
        // Verify that at least some drawing methods were called
        verify(mockGraphics, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }
    
    @Test
    public void testGetSlideItemOutOfBounds() {
        // Test accessing items outside valid range
        assertNull(slide.getSlideItem(-1), "Getting item at negative index should return null");
        assertNull(slide.getSlideItem(100), "Getting item at too large index should return null");
        
        // Add an item and test valid access
        TextItem item = new TextItem(1, "Test Item");
        slide.append(item);
        assertEquals(item, slide.getSlideItem(0), "Should return the correct item at valid index");
    }
}
