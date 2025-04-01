package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the Slide class
 */
public class SlideTest {
    
    private Slide slide;
    
    @BeforeEach
    void setUp() {
        slide = new Slide();
    }
    
    @Test
    void testNewSlide() {
        assertEquals(0, slide.getSize(), "New slide should have 0 items");
        assertNotNull(slide.getTitle(), "Title should not be null");
        assertEquals("", slide.getTitle(), "New slide should have empty title");
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
        
        assertEquals(2, slide.getSize(), "Slide should have 2 items after append");
        assertSame(item1, slide.getSlideItem(0), "First item should be item1");
        assertSame(item2, slide.getSlideItem(1), "Second item should be item2");
    }
    
    @Test
    void testGetSlideItemOutOfBounds() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            slide.getSlideItem(0); // No items yet, should throw exception
        }, "Getting item from empty slide should throw exception");
        
        slide.append(new TextItem(1, "Test"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            slide.getSlideItem(1); // Only one item, index 1 is out of bounds
        }, "Getting item with invalid index should throw exception");
    }
}
