package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

/**
 * Unit test for the exporter classes
 */
public class ExporterTest {
    
    @TempDir
    Path tempDir;
    
    private Presentation presentation;
    private HTMLExporter htmlExporter;
    private TextExporter textExporter;
    
    @BeforeEach
    void setUp() {
        // Create a test presentation with sample content
        presentation = new Presentation();
        presentation.setTitle("Test Presentation");
        
        // Add slides with content
        Slide slide1 = new Slide();
        slide1.setTitle("First Slide");
        slide1.append(new TextItem(1, "This is a test item on slide 1"));
        
        Slide slide2 = new Slide();
        slide2.setTitle("Second Slide");
        slide2.append(new TextItem(1, "This is a test item on slide 2"));
        
        presentation.addSlide(slide1);
        presentation.addSlide(slide2);
        
        // Create exporters
        htmlExporter = new HTMLExporter();
        textExporter = new TextExporter();
    }
    
    @Test
    void testHTMLExport() throws IOException {
        // Create a temporary file for the export
        File tempFile = tempDir.resolve("test_export.html").toFile();
        
        // Export the presentation
        htmlExporter.exportPresentation(presentation, tempFile.getAbsolutePath());
        
        // Verify the file was created
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
        
        // Read the file and check content
        String content = readFile(tempFile);
        
        // Verify HTML structure
        assertTrue(content.contains("<html>"));
        assertTrue(content.contains("</html>"));
        
        // Verify presentation title is included
        assertTrue(content.contains("Test Presentation"));
        
        // Verify slide content is included
        assertTrue(content.contains("First Slide"));
        assertTrue(content.contains("Second Slide"));
        assertTrue(content.contains("This is a test item on slide 1"));
        assertTrue(content.contains("This is a test item on slide 2"));
    }
    
    @Test
    void testTextExport() throws IOException {
        // Create a temporary file for the export
        File tempFile = tempDir.resolve("test_export.txt").toFile();
        
        // Export the presentation
        textExporter.exportPresentation(presentation, tempFile.getAbsolutePath());
        
        // Verify the file was created
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
        
        // Read the file and check content
        String content = readFile(tempFile);
        
        // Verify presentation title is included
        assertTrue(content.contains("Test Presentation"));
        
        // Verify slide content is included
        assertTrue(content.contains("First Slide"));
        assertTrue(content.contains("Second Slide"));
        assertTrue(content.contains("This is a test item on slide 1"));
        assertTrue(content.contains("This is a test item on slide 2"));
    }
    
    @Test
    void testExportErrors() {
        // Test with invalid file path
        String invalidPath = "/invalid/path/that/does/not/exist/test.html";
        
        // HTML Export with invalid path should throw IOException
        Exception exception = assertThrows(IOException.class, () -> {
            htmlExporter.exportPresentation(presentation, invalidPath);
        });
        
        // Text Export with invalid path should throw IOException
        exception = assertThrows(IOException.class, () -> {
            textExporter.exportPresentation(presentation, invalidPath);
        });
        
        // Test with null presentation
        final String validPath = tempDir.resolve("null_test.txt").toString();
        
        // Export with null presentation should throw NullPointerException
        exception = assertThrows(NullPointerException.class, () -> {
            textExporter.exportPresentation(null, validPath);
        });
    }
    
    // Helper method to read file content
    private String readFile(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024];
            int length;
            
            while ((length = reader.read(buffer)) > 0) {
                content.append(buffer, 0, length);
            }
            
            return content.toString();
        }
    }
}
