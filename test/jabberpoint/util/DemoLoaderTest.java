package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.CompositeSlideItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

/**
 * Unit test for the DemoLoader class
 */
public class DemoLoaderTest {
    
    private DemoLoader demoLoader;
    private Presentation presentation;
    
    @BeforeEach
    public void setUp() {
        demoLoader = new DemoLoader();
        presentation = new Presentation();
    }
    
    @Test
    public void testLoadPresentation() throws IOException {
        // Load the demo presentation
        demoLoader.loadPresentation(presentation, null);
        
        // Verify the presentation title
        assertEquals("Demo Presentation", presentation.getTitle());
        
        // Verify the number of slides
        assertEquals(4, presentation.getSize());
        
        // Test Slide 1: Introduction
        Slide slide1 = presentation.getSlide(0);
        assertEquals("JabberPoint", slide1.getTitle());
        assertEquals(6, slide1.getSize());
        
        // Verify the first slide item is a TextItem with the expected text
        SlideItem item = slide1.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals("The Java presentation tool", ((TextItem) item).getText());
        assertEquals(1, item.getLevel());
        
        // Test Slide 2: Features with image
        Slide slide2 = presentation.getSlide(1);
        assertEquals("Demo Slide 2", slide2.getTitle());
        assertEquals(2, slide2.getSize());
        
        // Verify the second item is a BitmapItem
        SlideItem imageItem = slide2.getSlideItem(1);
        assertTrue(imageItem instanceof BitmapItem);
        assertTrue(((BitmapItem) imageItem).getName().contains("JabberPoint.gif"));
        
        // Test Slide 3: Composite pattern example
        Slide slide3 = presentation.getSlide(2);
        assertEquals("Composite Pattern Example", slide3.getTitle());
        assertEquals(3, slide3.getSize());
        
        // Verify the second item is a CompositeSlideItem
        SlideItem compositeItem = slide3.getSlideItem(1);
        assertTrue(compositeItem instanceof CompositeSlideItem);
        CompositeSlideItem composite = (CompositeSlideItem) compositeItem;
        assertEquals("Group 1", composite.getName());
        assertEquals(2, composite.getSize());
        
        // Test Slide 4: Observer pattern example
        Slide slide4 = presentation.getSlide(3);
        assertEquals("Observer Pattern Demo", slide4.getTitle());
        assertEquals(5, slide4.getSize());
        
        // Verify the first item text
        SlideItem observerItem = slide4.getSlideItem(0);
        assertTrue(observerItem instanceof TextItem);
        assertEquals("The Observer pattern is used in JabberPoint to:", ((TextItem) observerItem).getText());
    }
    
    @Test
    public void testLoadPresentationWithEmptyPresentation() throws IOException {
        // Ensure the presentation starts empty
        assertEquals(0, presentation.getSize());
        assertNull(presentation.getTitle());
        
        // Load the demo presentation
        demoLoader.loadPresentation(presentation, "unused-filename");
        
        // Verify the presentation is no longer empty
        assertNotEquals(0, presentation.getSize());
        assertNotNull(presentation.getTitle());
    }
    
    @Test
    public void testLoadPresentationIgnoresFilename() throws IOException {
        // Load with null filename
        Presentation presentation1 = new Presentation();
        demoLoader.loadPresentation(presentation1, null);
        
        // Load with empty filename
        Presentation presentation2 = new Presentation();
        demoLoader.loadPresentation(presentation2, "");
        
        // Load with some filename
        Presentation presentation3 = new Presentation();
        demoLoader.loadPresentation(presentation3, "test.xml");
        
        // All should have the same content
        assertEquals(presentation1.getTitle(), presentation2.getTitle());
        assertEquals(presentation1.getTitle(), presentation3.getTitle());
        assertEquals(presentation1.getSize(), presentation2.getSize());
        assertEquals(presentation1.getSize(), presentation3.getSize());
    }
}
