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
    
    @TempDir
    Path tempDir; // Use a class-level TempDir to ensure it's available for all tests
    
    @BeforeEach
    public void setUp() throws IOException {
        xmlAccessor = new XMLAccessor();
        presentation = new Presentation();
        presentation.setTitle("Test Presentation");
        
        // Create the presentation.dtd file in the temp directory
        File dtdFile = tempDir.resolve("presentation.dtd").toFile();
        String dtdContent = "<!ELEMENT presentation (showtitle, slide*)>\n" +
                "<!ELEMENT showtitle (#PCDATA)>\n" +
                "<!ELEMENT slide (title, item*)>\n" +
                "<!ELEMENT title (#PCDATA)>\n" +
                "<!ELEMENT item (#PCDATA)>\n" +
                "<!ATTLIST item kind CDATA #REQUIRED>\n" +
                "<!ATTLIST item level CDATA #REQUIRED>";
        Files.writeString(dtdFile.toPath(), dtdContent);
    }
    
    @Test
    public void testLoadPresentation() throws IOException {
        // Create a simple XML file
        File xmlFile = tempDir.resolve("test.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"" + tempDir.resolve("presentation.dtd").toAbsolutePath() + "\">\n" +
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
        
        // Load the presentation from the XML file
        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the presentation title
        assertEquals("Test XML Presentation", presentation.getTitle());
        
        // Verify the number of slides
        assertEquals(2, presentation.getSize());
        
        // Verify the first slide
        Slide slide1 = presentation.getSlide(0);
        assertEquals("Test Slide 1", slide1.getTitle());
        assertEquals(2, slide1.getSize());
        
        // Verify the first slide's items
        TextItem item1 = (TextItem) slide1.getSlideItem(0);
        assertEquals(1, item1.getLevel());
        assertEquals("Text Item 1", item1.getText());
        
        TextItem item2 = (TextItem) slide1.getSlideItem(1);
        assertEquals(2, item2.getLevel());
        assertEquals("Text Item 2", item2.getText());
        
        // Verify the second slide
        Slide slide2 = presentation.getSlide(1);
        assertEquals("Test Slide 2", slide2.getTitle());
        assertEquals(1, slide2.getSize());
        
        // Verify the second slide's item
        TextItem item3 = (TextItem) slide2.getSlideItem(0);
        assertEquals(1, item3.getLevel());
        assertEquals("Text Item 3", item3.getText());
    }
    
    @Test
    public void testSavePresentation() throws IOException {
        // Create a presentation with slides and items
        presentation.setTitle("Test Save Presentation");
        
        // Create slide 1
        Slide slide1 = new Slide();
        slide1.setTitle("Save Test Slide 1");
        slide1.append(new TextItem(1, "Save Text Item 1"));
        slide1.append(new TextItem(2, "Save Text Item 2"));
        presentation.addSlide(slide1);
        
        // Create slide 2
        Slide slide2 = new Slide();
        slide2.setTitle("Save Test Slide 2");
        slide2.append(new TextItem(1, "Save Text Item 3"));
        presentation.addSlide(slide2);
        
        // Save the presentation to a temporary file
        File xmlFile = tempDir.resolve("save_test.xml").toFile();
        xmlAccessor.savePresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the file exists
        assertTrue(xmlFile.exists());
        
        // Load the saved file into a new presentation to verify
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadPresentation(loadedPresentation, xmlFile.getAbsolutePath());
        
        // Verify the loaded presentation matches the original
        assertEquals(presentation.getTitle(), loadedPresentation.getTitle());
        assertEquals(presentation.getSize(), loadedPresentation.getSize());
        
        // Verify the first slide
        Slide loadedSlide1 = loadedPresentation.getSlide(0);
        assertEquals(slide1.getTitle(), loadedSlide1.getTitle());
        assertEquals(slide1.getSize(), loadedSlide1.getSize());
        
        // Verify the first slide's items
        TextItem loadedItem1 = (TextItem) loadedSlide1.getSlideItem(0);
        assertEquals(1, loadedItem1.getLevel());
        assertEquals("Save Text Item 1", loadedItem1.getText());
        
        TextItem loadedItem2 = (TextItem) loadedSlide1.getSlideItem(1);
        assertEquals(2, loadedItem2.getLevel());
        assertEquals("Save Text Item 2", loadedItem2.getText());
        
        // Verify the second slide
        Slide loadedSlide2 = loadedPresentation.getSlide(1);
        assertEquals(slide2.getTitle(), loadedSlide2.getTitle());
        assertEquals(slide2.getSize(), loadedSlide2.getSize());
        
        // Verify the second slide's item
        TextItem loadedItem3 = (TextItem) loadedSlide2.getSlideItem(0);
        assertEquals(1, loadedItem3.getLevel());
        assertEquals("Save Text Item 3", loadedItem3.getText());
    }
    
    @Test
    public void testSaveAndLoadBitmapItem() throws IOException {
        // Create a presentation with a slide containing a bitmap item
        presentation.setTitle("Bitmap Test");
        
        // Create a slide with a bitmap item
        Slide slide = new Slide();
        slide.setTitle("Bitmap Slide");
        slide.append(new BitmapItem(1, "JabberPoint.gif"));
        presentation.addSlide(slide);
        
        // Save the presentation to a temporary file
        File xmlFile = tempDir.resolve("bitmap_test.xml").toFile();
        xmlAccessor.savePresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the file exists
        assertTrue(xmlFile.exists());
        
        // Load the saved file into a new presentation
        Presentation loadedPresentation = new Presentation();
        xmlAccessor.loadPresentation(loadedPresentation, xmlFile.getAbsolutePath());
        
        // Verify the loaded presentation
        assertEquals("Bitmap Test", loadedPresentation.getTitle());
        assertEquals(1, loadedPresentation.getSize());
        
        // Verify the slide
        Slide loadedSlide = loadedPresentation.getSlide(0);
        assertEquals("Bitmap Slide", loadedSlide.getTitle());
        assertEquals(1, loadedSlide.getSize());
        
        // Verify the bitmap item
        assertTrue(loadedSlide.getSlideItem(0) instanceof BitmapItem);
        BitmapItem loadedItem = (BitmapItem) loadedSlide.getSlideItem(0);
        assertEquals(1, loadedItem.getLevel());
        assertTrue(loadedItem.getName().contains("JabberPoint.gif"));
    }
    
    @Test
    public void testLoadPresentationWithInvalidLevelFormat() throws IOException {
        // Create an XML file with an invalid level format
        File xmlFile = tempDir.resolve("invalid_level.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"" + tempDir.resolve("presentation.dtd").toAbsolutePath() + "\">\n" +
                "<presentation>\n" +
                "    <showtitle>Invalid Level Test</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Invalid Level Slide</title>\n" +
                "        <item level=\"invalid\" kind=\"text\">Invalid Level Item</item>\n" +
                "    </slide>\n" +
                "</presentation>";
        Files.writeString(xmlFile.toPath(), xmlContent);
        
        // Load the presentation - should handle the invalid level gracefully
        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the presentation was loaded
        assertEquals("Invalid Level Test", presentation.getTitle());
        assertEquals(1, presentation.getSize());
        
        // Verify the slide
        Slide slide = presentation.getSlide(0);
        assertEquals("Invalid Level Slide", slide.getTitle());
        assertEquals(1, slide.getSize());
        
        // Verify the item has a default level (should be 1)
        TextItem item = (TextItem) slide.getSlideItem(0);
        assertEquals(1, item.getLevel());
        assertEquals("Invalid Level Item", item.getText());
    }
    
    @Test
    public void testLoadPresentationWithUnknownItemType() throws IOException {
        // Create an XML file with an unknown item type
        File xmlFile = tempDir.resolve("unknown_type.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"" + tempDir.resolve("presentation.dtd").toAbsolutePath() + "\">\n" +
                "<presentation>\n" +
                "    <showtitle>Unknown Type Test</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Unknown Type Slide</title>\n" +
                "        <item level=\"1\" kind=\"unknown\">Unknown Type Item</item>\n" +
                "    </slide>\n" +
                "</presentation>";
        Files.writeString(xmlFile.toPath(), xmlContent);
        
        // Load the presentation - should handle the unknown type gracefully
        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the presentation was loaded
        assertEquals("Unknown Type Test", presentation.getTitle());
        assertEquals(1, presentation.getSize());
        
        // Verify the slide
        Slide slide = presentation.getSlide(0);
        assertEquals("Unknown Type Slide", slide.getTitle());
        
        // The unknown item type should be ignored, so the slide should have no items
        assertEquals(0, slide.getSize());
    }
    
    @Test
    public void testLoadPresentationWithImagePathPrefix() throws IOException {
        // Create an XML file with an image that has a path prefix
        File xmlFile = tempDir.resolve("image_prefix.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"" + tempDir.resolve("presentation.dtd").toAbsolutePath() + "\">\n" +
                "<presentation>\n" +
                "    <showtitle>Image Prefix Test</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Image Prefix Slide</title>\n" +
                "        <item level=\"1\" kind=\"image\">img/JabberPoint.gif</item>\n" +
                "    </slide>\n" +
                "</presentation>";
        Files.writeString(xmlFile.toPath(), xmlContent);
        
        // Load the presentation
        xmlAccessor.loadPresentation(presentation, xmlFile.getAbsolutePath());
        
        // Verify the presentation was loaded
        assertEquals("Image Prefix Test", presentation.getTitle());
        assertEquals(1, presentation.getSize());
        
        // Verify the slide
        Slide slide = presentation.getSlide(0);
        assertEquals("Image Prefix Slide", slide.getTitle());
        assertEquals(1, slide.getSize());
        
        // Verify the image item
        assertTrue(slide.getSlideItem(0) instanceof BitmapItem);
        BitmapItem item = (BitmapItem) slide.getSlideItem(0);
        assertEquals(1, item.getLevel());
        assertEquals("img/JabberPoint.gif", item.getName());
    }
}
