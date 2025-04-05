package jabberpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.Observer;

/**
 * Test class explicitly designed to improve branch coverage for the Presentation class
 * Each test explicitly targets branch conditions
 */
public class PresentationBranchCoverageTest {
    
    private static class TestObserver implements Observer {
        private int updateCount = 0;
        
        @Override
        public void update(Presentation presentation, Slide slide) {
            updateCount++;
        }
        
        public int getUpdateCount() {
            return updateCount;
        }
    }
    
    @Test
    void testSetSlideNumberBranches() {
        Presentation presentation = new Presentation();
        
        // First branch: slides.size() == 0
        // This should not throw an exception as -1 is valid for empty presentations
        presentation.setSlideNumber(-1);
        assertEquals(-1, presentation.getSlideNumber());
        
        // Add a slide and try setting valid and invalid numbers
        Slide slide = new Slide();
        presentation.addSlide(slide);
        
        // Valid slide number (within bounds)
        presentation.setSlideNumber(0);
        assertEquals(0, presentation.getSlideNumber());
        
        try {
            // Invalid slide number (too high) - should throw exception
            presentation.setSlideNumber(1); // Only have 1 slide, so index 1 is invalid
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
        
        try {
            // Invalid slide number (too low) - should throw exception
            presentation.setSlideNumber(-2);
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
    
    @Test
    void testRemoveSlideBranches() {
        Presentation presentation = new Presentation();
        
        // Add several slides
        presentation.addSlide(new Slide());
        presentation.addSlide(new Slide());
        presentation.addSlide(new Slide());
        
        // Set current slide to the middle
        presentation.setSlideNumber(1);
        
        // Remove slide at index higher than current - current should stay the same
        presentation.removeSlide(2);
        assertEquals(1, presentation.getSlideNumber());
        
        // Remove slide at current index - current should adjust
        presentation.removeSlide(1);
        assertEquals(0, presentation.getSlideNumber());
        
        // Add more slides
        presentation.addSlide(new Slide());
        presentation.addSlide(new Slide());
        
        // Set to last slide
        presentation.setSlideNumber(2);
        
        // Remove last slide - current should adjust
        presentation.removeSlide(2);
        assertEquals(1, presentation.getSlideNumber());
        
        try {
            // Try removing with invalid index
            presentation.removeSlide(5);
            fail("Should have thrown IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
    
    @Test
    void testGetSlideBranches() {
        Presentation presentation = new Presentation();
        
        // Add several slides
        Slide slide0 = new Slide();
        Slide slide1 = new Slide();
        presentation.addSlide(slide0);
        presentation.addSlide(slide1);
        
        // Test with valid indices
        assertEquals(slide0, presentation.getSlide(0));
        assertEquals(slide1, presentation.getSlide(1));
        
        // Test with current slide when current is valid
        presentation.setSlideNumber(1);
        assertEquals(slide1, presentation.getCurrentSlide());
        
        // Test with current slide when current is -1
        presentation.setSlideNumber(-1);
        assertNull(presentation.getCurrentSlide());
        
        try {
            // Test with invalid index
            presentation.getSlide(5);
            fail("Should have thrown ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
    
    @Test
    void testObserverBranches() {
        Presentation presentation = new Presentation();
        TestObserver observer1 = new TestObserver();
        TestObserver observer2 = new TestObserver();
        
        // Add observers
        presentation.addObserver(observer1);
        presentation.addObserver(observer2);
        
        // Try adding duplicate (branch condition)
        presentation.addObserver(observer1);
        
        // Make a change to trigger notification
        presentation.addSlide(new Slide());
        
        // Verify both observers were notified exactly once
        assertEquals(1, observer1.getUpdateCount());
        assertEquals(1, observer2.getUpdateCount());
        
        // Remove an observer
        presentation.removeObserver(observer1);
        
        // Make another change
        presentation.addSlide(new Slide());
        
        // Verify only observer2 got another notification
        assertEquals(1, observer1.getUpdateCount());
        assertEquals(2, observer2.getUpdateCount());
        
        // Try removing observer that's not in the list (branch condition)
        presentation.removeObserver(observer1);
    }
}
