package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import jabberpoint.model.Presentation;

/**
 * Unit test for the PresentationLoaderContext class
 */
public class PresentationLoaderContextTest {
    
    private PresentationLoaderContext context;
    private PresentationLoader mockLoader;
    private Presentation mockPresentation;
    
    @BeforeEach
    public void setUp() {
        mockLoader = mock(PresentationLoader.class);
        mockPresentation = mock(Presentation.class);
        context = new PresentationLoaderContext(mockLoader);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor sets the loader strategy
        assertEquals(mockLoader, context.getLoaderStrategy());
    }
    
    @Test
    public void testSetLoaderStrategy() {
        // Create a new mock loader
        PresentationLoader newMockLoader = mock(PresentationLoader.class);
        
        // Set the new loader strategy
        context.setLoaderStrategy(newMockLoader);
        
        // Verify the loader strategy was changed
        assertEquals(newMockLoader, context.getLoaderStrategy());
        assertNotEquals(mockLoader, context.getLoaderStrategy());
    }
    
    @Test
    public void testLoadPresentation() throws IOException {
        // Test that loadPresentation delegates to the loader strategy
        String source = "test.xml";
        
        context.loadPresentation(mockPresentation, source);
        
        // Verify the loader strategy's loadPresentation method was called
        verify(mockLoader).loadPresentation(mockPresentation, source);
    }
    
    @Test
    public void testLoadPresentationWithNullStrategy() {
        // Create a context with null strategy
        context = new PresentationLoaderContext(null);
        
        // Attempt to load a presentation should throw IllegalStateException
        assertThrows(IllegalStateException.class, () -> {
            context.loadPresentation(mockPresentation, "test.xml");
        });
    }
    
    @Test
    public void testLoadPresentationWithDifferentStrategies() throws IOException {
        // Create mock loaders
        PresentationLoader xmlLoader = mock(PresentationLoader.class);
        PresentationLoader demoLoader = mock(PresentationLoader.class);
        
        // Create a context with the XML loader
        context = new PresentationLoaderContext(xmlLoader);
        
        // Load a presentation with the XML loader
        context.loadPresentation(mockPresentation, "test.xml");
        
        // Verify the XML loader was used
        verify(xmlLoader).loadPresentation(mockPresentation, "test.xml");
        verify(demoLoader, never()).loadPresentation(any(), any());
        
        // Change to the demo loader
        context.setLoaderStrategy(demoLoader);
        
        // Load another presentation
        context.loadPresentation(mockPresentation, "demo");
        
        // Verify the demo loader was used
        verify(demoLoader).loadPresentation(mockPresentation, "demo");
    }
    
    @Test
    public void testGetLoaderStrategy() {
        // Test that getLoaderStrategy returns the current loader strategy
        assertEquals(mockLoader, context.getLoaderStrategy());
        
        // Change the loader strategy
        PresentationLoader newMockLoader = mock(PresentationLoader.class);
        context.setLoaderStrategy(newMockLoader);
        
        // Verify getLoaderStrategy returns the new loader strategy
        assertEquals(newMockLoader, context.getLoaderStrategy());
    }
}
