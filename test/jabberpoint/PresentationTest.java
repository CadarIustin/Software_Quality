package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;

/**
 * Unit test for the Presentation class
 */
public class PresentationTest {
    
    private Presentation presentation;
    
    @BeforeEach
    void setUp() {
        presentation = new Presentation();
    }
    
    @Test
    void testNewPresentation() {
        assertEquals(0, presentation.getSlideNumber(), "Initial slide number should be 0");
        assertEquals(0, presentation.getSize(), "New presentation should have 0 slides");
    }
    
    @Test
    void testSlideNavigation() {
        // Add two sample slides
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);
        
        assertEquals(0, presentation.getSlideNumber(), "Initial slide number should be 0");
        assertEquals(2, presentation.getSize(), "Presentation should have 2 slides");
        
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber(), "After nextSlide, slide number should be 1");
        
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber(), "After nextSlide beyond last slide, slide number should remain 1");
        
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber(), "After previousSlide, slide number should be 0");
        
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber(), "After previousSlide beyond first slide, slide number should remain 0");
    }
    
    @Test
    void testGetCurrentSlide() {
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);
        
        assertSame(slide1, presentation.getCurrentSlide(), "getCurrentSlide should return the first slide");
        
        presentation.nextSlide();
        assertSame(slide2, presentation.getCurrentSlide(), "getCurrentSlide should return the second slide");
    }
    
    @Test
    void testClear() {
        Slide slide = new Slide();
        presentation.addSlide(slide);
        assertEquals(1, presentation.getSize(), "Presentation should have 1 slide");
        
        presentation.clear();
        assertEquals(0, presentation.getSize(), "After clear, presentation should have 0 slides");
        assertEquals(0, presentation.getSlideNumber(), "After clear, slide number should be 0");
    }
    
    @Test
    void testSetSlideNumberBoundaries() {
        // Test invalid slide number below lower bound
        assertThrows(IllegalArgumentException.class, () -> {
            presentation.setSlideNumber(-2);
        });
        
        // Add a slide to test upper bound
        Slide slide = new Slide();
        presentation.addSlide(slide);
        
        // Test valid slide number
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
        
        // Test invalid slide number above upper bound
        assertThrows(IllegalArgumentException.class, () -> {
            presentation.setSlideNumber(1);
        });
    }
    
    @Test
    void testRemoveSlideWithBranchCoverage() {
        // Add two slides
        Slide slide1 = new Slide();
        Slide slide2 = new Slide();
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);
        
        // Test removing with invalid index (negative)
        presentation.removeSlide(-1);
        assertEquals(2, presentation.getSize(), "Nothing should be removed with negative index");
        
        // Test removing with invalid index (too large)
        presentation.removeSlide(5);
        assertEquals(2, presentation.getSize(), "Nothing should be removed with too large index");
        
        // Set slide number to last slide
        presentation.setSlideNumber(1);
        assertEquals(1, presentation.getSlideNumber());
        
        // Remove last slide - should adjust currentSlideNumber
        presentation.removeSlide(1);
        assertEquals(1, presentation.getSize(), "One slide should remain");
        assertEquals(0, presentation.getSlideNumber(), "Current slide should be adjusted to new last slide");
        
        // Add slide back
        presentation.addSlide(slide2);
        presentation.setSlideNumber(0);
        
        // Remove first slide - currentSlideNumber should remain the same
        presentation.removeSlide(0);
        assertEquals(1, presentation.getSize(), "One slide should remain");
        assertEquals(0, presentation.getSlideNumber(), "Current slide number should remain the same");
    }
    
    @Test
    void testGetSlideWithBranchCoverage() {
        // Test getting slide with negative index
        assertNull(presentation.getSlide(-1), "Should return null for negative index");
        
        // Test getting slide with too large index
        assertNull(presentation.getSlide(0), "Should return null for non-existent slide");
        
        // Add a slide and test valid retrieval
        Slide slide = new Slide();
        presentation.addSlide(slide);
        assertSame(slide, presentation.getSlide(0), "Should return the correct slide for valid index");
    }
    
    @Test
    void testObserverOperations() {
        // Create a mock observer
        TestObserver observer = new TestObserver();
        
        // Add the observer
        presentation.addObserver(observer);
        
        // Trigger a notification
        presentation.setTitle("New Title");
        assertEquals(1, observer.updateCount, "Observer should be notified once");
        
        // Add same observer again (should not be added twice)
        presentation.addObserver(observer);
        presentation.setTitle("Another Title");
        assertEquals(2, observer.updateCount, "Observer should be notified once more");
        
        // Remove observer
        presentation.removeObserver(observer);
        presentation.setTitle("Final Title");
        assertEquals(2, observer.updateCount, "Observer should not be notified after removal");
    }
    
    // Simple observer implementation for testing
    private static class TestObserver implements jabberpoint.model.Observer {
        int updateCount = 0;
        
        @Override
        public void update(Presentation presentation, Slide slide) {
            updateCount++;
        }
    }
}
