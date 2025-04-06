package jabberpoint.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.awt.Graphics2D;
import java.awt.Rectangle;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

/**
 * Unit test for the SlideViewerComponent class
 */
public class SlideViewerComponentTest {
    
    private SlideViewerComponent slideViewer;
    private Presentation presentation;
    
    @BeforeEach
    void setUp() {
        presentation = new Presentation();
        
        // Create test slide
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(new TextItem(1, "Test Item"));
        presentation.addSlide(slide);
        
        // Set current slide
        presentation.setSlideNumber(0);
        
        // Create the component
        slideViewer = new SlideViewerComponent(presentation);
    }
    
    @Test
    void testInitialization() {
        assertNotNull(slideViewer);
        assertNotNull(slideViewer.getSlide());
    }
    
    @Test
    void testUpdate() {
        // Create a mock of the slide with a different title to simulate change
        Slide newSlide = new Slide();
        newSlide.setTitle("Updated Slide");
        
        // Add the slide to the presentation
        presentation.addSlide(newSlide);
        
        // Change to the new slide
        presentation.setSlideNumber(1);
        
        // The update method should be called via Observer pattern
        // We don't have a direct way to check this without making fields accessible,
        // but we can verify the component doesn't crash during update
        assertDoesNotThrow(() -> {
            slideViewer.update(presentation, newSlide);
        });
        
        // Verify the slide was updated
        assertEquals(newSlide, slideViewer.getSlide());
    }
    
    @Test
    void testGetPreferredSize() {
        // Get preferred size
        java.awt.Dimension size = slideViewer.getPreferredSize();
        
        // Should be non-zero
        assertTrue(size.width > 0);
        assertTrue(size.height > 0);
    }
    
    @Test
    void testPaintComponent() {
        // Create a Graphics2D mock instead of Graphics
        Graphics2D mockGraphics = mock(Graphics2D.class);
        
        // Mock the getClipBounds method to return a rectangle
        when(mockGraphics.getClipBounds()).thenReturn(new Rectangle(0, 0, 800, 600));
        
        // Call paintComponent
        assertDoesNotThrow(() -> {
            slideViewer.paintComponent(mockGraphics);
        });
        
        // It's difficult to verify what was drawn without making fields accessible
        // but we can at least verify the method doesn't crash
    }
    
    @Test
    void testUpdateWithNullSlide() {
        // Test the update method with null slide
        assertDoesNotThrow(() -> {
            slideViewer.update(presentation, null);
        });
    }
}
