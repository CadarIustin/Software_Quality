package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

/**
 * Unit test for the XMLAccessor class
 */
public class XMLAccessorTest {
    
    private XMLAccessor xmlAccessor;
    private Presentation presentation;
    
    @BeforeEach
    public void setUp() {
        xmlAccessor = new XMLAccessor();
        presentation = new Presentation();
        presentation.setTitle("Test Presentation");
    }
    
    @Test
    public void testLoadPresentation(@TempDir Path tempDir) throws IOException {
        // Create a simple XML file
        File xmlFile = tempDir.resolve("test.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"presentation.dtd\">\n" +
                "<presentation>\n" +
                "    <showtitle>Test XML Presentation</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Test Slide 1</title>\n" +
                "        <item level=\"1\" kind=\"text\">Text Item 1</item>\n" +
                "        <item level=\"2\" kind=\"text\">Text Item 2</item>\n" +
                "    </slide>\n" +
                "    <slide>\n" +
                "        <title>Test Slide 2</title>\n" +
                "        <item level=\"1\" kind=\"text\">Text Item 3</item>\n" +
                "    </slide>\n" +
                "</presentation>";
        Files.writeString(xmlFile.toPath(), xmlContent);
        
        // Load the presentation
        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the presentation was loaded correctly
        assertEquals("Test XML Presentation", presentation.getTitle());
        assertEquals(2, presentation.getSize());
        
        // Check first slide
        Slide slide1 = presentation.getSlide(0);
        assertEquals("Test Slide 1", slide1.getTitle());
        assertEquals(2, slide1.getSize());
        assertTrue(slide1.getSlideItem(0) instanceof TextItem);
        assertEquals("Text Item 1", ((TextItem) slide1.getSlideItem(0)).getText());
        assertEquals(1, slide1.getSlideItem(0).getLevel());
        
        // Check second slide
        Slide slide2 = presentation.getSlide(1);
        assertEquals("Test Slide 2", slide2.getTitle());
        assertEquals(1, slide2.getSize());
        assertTrue(slide2.getSlideItem(0) instanceof TextItem);
        assertEquals("Text Item 3", ((TextItem) slide2.getSlideItem(0)).getText());
    }
    
    @Test
    public void testSavePresentation(@TempDir Path tempDir) throws IOException {
        // Create a presentation with slides and items
        Slide slide1 = new Slide();
        slide1.setTitle("Saved Slide 1");
        slide1.append(new TextItem(1, "Text Item 1"));
        slide1.append(new TextItem(2, "Text Item 2"));
        
        Slide slide2 = new Slide();
        slide2.setTitle("Saved Slide 2");
        slide2.append(new TextItem(1, "Text Item 3"));
        
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);
        
        // Save the presentation
        File xmlFile = tempDir.resolve("saved.xml").toFile();
        xmlAccessor.savePresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the file was created
        assertTrue(xmlFile.exists());
        
        // Load the saved presentation into a new presentation object
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadPresentation(loadedPresentation, xmlFile.getAbsolutePath());
        
        // Verify the loaded presentation matches the original
        assertEquals(presentation.getTitle(), loadedPresentation.getTitle());
        assertEquals(presentation.getSize(), loadedPresentation.getSize());
        
        // Check first slide
        Slide loadedSlide1 = loadedPresentation.getSlide(0);
        assertEquals(slide1.getTitle(), loadedSlide1.getTitle());
        assertEquals(slide1.getSize(), loadedSlide1.getSize());
        
        // Check second slide
        Slide loadedSlide2 = loadedPresentation.getSlide(1);
        assertEquals(slide2.getTitle(), loadedSlide2.getTitle());
        assertEquals(slide2.getSize(), loadedSlide2.getSize());
    }
    
    @Test
    public void testSaveAndLoadBitmapItem(@TempDir Path tempDir) throws IOException {
        // Create a presentation with a slide containing a bitmap item
        Slide slide = new Slide();
        slide.setTitle("Bitmap Slide");
        slide.append(new BitmapItem(1, "img/JabberPoint.gif"));
        
        presentation.addSlide(slide);
        
        // Save the presentation
        File xmlFile = tempDir.resolve("bitmap.xml").toFile();
        xmlAccessor.savePresentation(presentation, xmlFile.getAbsolutePath());
        
        // Load the saved presentation
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadPresentation(loadedPresentation, xmlFile.getAbsolutePath());
        
        // Verify the bitmap item was saved and loaded correctly
        Slide loadedSlide = loadedPresentation.getSlide(0);
        assertTrue(loadedSlide.getSlideItem(0) instanceof BitmapItem);
        BitmapItem loadedBitmap = (BitmapItem) loadedSlide.getSlideItem(0);
        assertEquals("img/JabberPoint.gif", loadedBitmap.getName());
    }
}
