package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.Observer;

/**
 * Unit test for the Presentation class
 */
public class PresentationTest {
    
    private Presentation presentation;
    
    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        // Add a slide to initialize currentSlideNumber properly
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        presentation.addSlide(slide);
    }
    
    @Test
    void testNewPresentation() {
        // With our initialization in setUp, slide number should be 0
        assertEquals(0, presentation.getSlideNumber(), "Initial slide number should be 0");
        assertEquals(1, presentation.getSize(), "Presentation should have 1 slide after setup");
    }
    
    @Test
    void testSlideNavigation() {
        // Add another sample slide (we already have one from setUp)
        Slide slide2 = new Slide();
        slide2.setTitle("Test Slide 2");
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
        Slide currentSlide = presentation.getCurrentSlide();
        assertNotNull(currentSlide, "getCurrentSlide should return the first slide");
        assertEquals("Test Slide", currentSlide.getTitle(), "Slide title should match");
    }
    
    @Test
    void testTitle() {
        String testTitle = "Test Presentation Title";
        presentation.setTitle(testTitle);
        assertEquals(testTitle, presentation.getTitle(), "getTitle should return the set title");
    }
    
    @Test
    void testClear() {
        // Add a few more slides
        Slide slide2 = new Slide();
        Slide slide3 = new Slide();
        presentation.addSlide(slide2);
        presentation.addSlide(slide3);
        
        // Set slide number to something higher
        presentation.setSlideNumber(2);
        
        // Clear the presentation
        presentation.clear();
        
        // After clear, slide count should be 0 and slide number should be 0
        assertEquals(0, presentation.getSize(), "After clear, size should be 0");
        assertEquals(0, presentation.getSlideNumber(), "After clear, slide number should be 0");
    }
    
    @Test
    void testObserverPattern() {
        // Create a mock observer
        MockObserver observer = new MockObserver();
        
        // Register the observer
        presentation.addObserver(observer);
        
        // Trigger a change that should notify observers
        presentation.nextSlide();
        
        // Verify the observer was notified
        assertTrue(observer.wasNotified, "Observer should be notified");
        
        // Remove the observer
        presentation.removeObserver(observer);
        
        // Reset the notification flag
        observer.wasNotified = false;
        
        // Trigger another change
        presentation.previousSlide();
        
        // Verify the observer was not notified after removal
        assertFalse(observer.wasNotified, "Removed observer should not be notified");
    }
    
    @Test
    void testGetSlide() {
        // Add a second slide with a specific title
        Slide slide2 = new Slide();
        slide2.setTitle("Second Slide");
        presentation.addSlide(slide2);
        
        // Get the slide by number
        Slide retrievedSlide = presentation.getSlide(1);
        assertNotNull(retrievedSlide, "getSlide should return a slide");
        assertEquals("Second Slide", retrievedSlide.getTitle(), "Slide title should match");
    }
    
    @Test
    void testRemoveSlide() {
        // Add a second slide
        Slide slide2 = new Slide();
        slide2.setTitle("Slide to Remove");
        presentation.addSlide(slide2);
        
        assertEquals(2, presentation.getSize(), "Should have 2 slides before removal");
        
        // Remove the second slide
        presentation.removeSlide(1);
        
        assertEquals(1, presentation.getSize(), "Should have 1 slide after removal");
        assertEquals("Test Slide", presentation.getSlide(0).getTitle(), "Remaining slide should be the original");
    }
    
    // Mock Observer class for testing the Observer pattern
    private static class MockObserver implements Observer {
        boolean wasNotified = false;
        
        @Override
        public void update(Presentation presentation, Slide slide) {
            wasNotified = true;
        }
    }
}
