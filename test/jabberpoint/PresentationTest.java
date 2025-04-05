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
}
