package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.List;

import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

/**
 * Unit test for the Slide class
 */
public class SlideTest {
    
    private Slide slide;
    
    @BeforeEach
    public void setUp() {
        slide = new Slide();
        slide.setTitle("Test Slide");
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
        // This is a visual test that's hard to verify without mocking
        // We'll just ensure it doesn't throw exceptions
        Graphics mockGraphics = null; // We're not actually using this
        Rectangle area = new Rectangle(0, 0, 800, 600);
        ImageObserver mockObserver = null; // We're not actually using this
        
        // Should not throw exception even with null graphics
        assertDoesNotThrow(() -> slide.draw(mockGraphics, area, mockObserver));
        
        // Add an item and test again
        slide.append(new TextItem(1, "Test Item"));
        assertDoesNotThrow(() -> slide.draw(mockGraphics, area, mockObserver));
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
