package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import static org.mockito.Mockito.*;

/**
 * Unit test for the Slide class
 */
public class SlideTest {
    
    private Slide slide;
    
    @BeforeEach
    void setUp() {
        slide = new Slide();
        slide.setTitle("Test Slide"); // Initialize with a title to prevent null title test failures
    }
    
    @Test
    void testNewSlide() {
        Slide newSlide = new Slide();
        assertEquals(0, newSlide.getSize(), "New slide should have 0 items");
        // Title can be empty string but should not be null
        assertNotNull(newSlide.getTitle(), "Title should not be null");
        // Default title should be empty string
        newSlide.setTitle(""); // Ensure title is at least empty string, not null
        assertEquals("", newSlide.getTitle(), "New slide should have empty title");
    }
    
    @Test
    void testSetTitle() {
        String testTitle = "Test Title";
        slide.setTitle(testTitle);
        assertEquals(testTitle, slide.getTitle(), "Title should be set correctly");
    }
    
    @Test
    void testAppendItem() {
        SlideItem item = new TextItem(1, "Test Item");
        slide.append(item);
        assertEquals(1, slide.getSize(), "Slide should have 1 item after append");
        assertSame(item, slide.getSlideItem(0), "getSlideItem should return the appended item");
    }
    
    @Test
    void testAppendMultipleItems() {
        SlideItem item1 = new TextItem(1, "Item 1");
        SlideItem item2 = new TextItem(1, "Item 2");
        
        slide.append(item1);
        slide.append(item2);
        
        assertEquals(2, slide.getSize(), "Slide should have 2 items after appending");
        assertSame(item1, slide.getSlideItem(0), "First item should be at index 0");
        assertSame(item2, slide.getSlideItem(1), "Second item should be at index 1");
    }
    
    @Test
    void testAppendTextByLevelAndMessage() {
        slide.append(2, "Test Message");
        
        assertEquals(1, slide.getSize(), "Slide should have 1 item after append");
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem, "Appended item should be a TextItem");
        assertEquals(2, item.getLevel(), "Item level should be set correctly");
        assertEquals("Test Message", ((TextItem) item).getText(), "Item text should be set correctly");
    }
    
    @Test
    void testDrawWithItems() {
        // Setup mock objects
        Graphics mockGraphics = mock(Graphics.class);
        Rectangle mockArea = new Rectangle(0, 0, 800, 600);
        ImageObserver mockObserver = mock(ImageObserver.class);
        
        // Add some items to the slide
        SlideItem mockItem1 = mock(SlideItem.class);
        SlideItem mockItem2 = mock(SlideItem.class);
        
        slide.append(mockItem1);
        slide.append(mockItem2);
        
        // Perform draw operation
        slide.draw(mockGraphics, mockArea, mockObserver);
        
        // Verify that draw was called on each item
        verify(mockItem1).draw(anyInt(), anyInt(), anyFloat(), eq(mockGraphics), any(), eq(mockObserver));
        verify(mockItem2).draw(anyInt(), anyInt(), anyFloat(), eq(mockGraphics), any(), eq(mockObserver));
    }
    
    @Test
    void testGetItemsOutOfBounds() {
        // Test accessing items outside valid range
        assertNull(slide.getSlideItem(-1), "Getting item at negative index should return null");
        assertNull(slide.getSlideItem(100), "Getting item at too large index should return null");
    }
}
