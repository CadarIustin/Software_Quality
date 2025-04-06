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
    public void testSetSlideNumberBranches() {
        Presentation presentation = new Presentation();
        
        // With our changes, we start at 0, not -1
        assertEquals(0, presentation.getSlideNumber());
        
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
    public void testRemoveSlideBranches() {
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
        
        // Test invalid removal - our implementation silently ignores invalid indices
        int sizeBefore = presentation.getSize();
        presentation.removeSlide(5); // Should do nothing
        assertEquals(sizeBefore, presentation.getSize(), "Size should not change when removing invalid index");
    }
    
    @Test
    public void testGetSlideBranches() {
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
        
        // Test invalid index - should return null
        assertNull(presentation.getSlide(5), "Getting invalid slide index should return null");
    }
    
    @Test
    public void testSlideNavigationBranches() {
        Presentation presentation = new Presentation();
        
        // Test prev/next on empty presentation (shouldn't throw exceptions)
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        // Add one slide
        presentation.addSlide(new Slide());
        
        // Forward navigation - should stay at 0 with one slide
        presentation.nextSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        // Backward navigation - should stay at 0
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        // Add a second slide
        presentation.addSlide(new Slide());
        
        // Forward navigation - should move to slide 1
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        
        // Forward again - should stay at 1 (last slide)
        presentation.nextSlide();
        assertEquals(1, presentation.getSlideNumber());
        
        // Backward navigation - should move to slide 0
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber());
        
        // Backward again - should stay at 0 (first slide)
        presentation.previousSlide();
        assertEquals(0, presentation.getSlideNumber());
    }
    
    @Test
    public void testObserverPatternBranches() {
        Presentation presentation = new Presentation();
        TestObserver observer1 = new TestObserver();
        TestObserver observer2 = new TestObserver();
        
        // Add observers
        presentation.addObserver(observer1);
        presentation.addObserver(observer1); // Try adding same observer twice (should be ignored)
        presentation.addObserver(observer2);
        
        // Trigger notification
        presentation.setTitle("New Title");
        
        // Both observers should have been notified once
        assertEquals(1, observer1.getUpdateCount());
        assertEquals(1, observer2.getUpdateCount());
        
        // Remove observer
        presentation.removeObserver(observer1);
        
        // Trigger notification again
        presentation.setSlideNumber(0);
        
        // observer1 should not be notified again, observer2 should be
        assertEquals(1, observer1.getUpdateCount());
        assertEquals(2, observer2.getUpdateCount());
        
        // Remove non-existing observer (should not throw)
        presentation.removeObserver(new TestObserver());
    }
    
    @Test
    public void testClearMethod() {
        Presentation presentation = new Presentation();
        presentation.addSlide(new Slide());
        presentation.addSlide(new Slide());
        
        assertEquals(2, presentation.getSize());
        
        // Test clear
        presentation.clear();
        
        // After clear, should have 0 slides and slide number should be 0
        assertEquals(0, presentation.getSize());
        assertEquals(0, presentation.getSlideNumber());
    }
}
