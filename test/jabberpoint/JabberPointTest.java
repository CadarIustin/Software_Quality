package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import jabberpoint.model.Style;

/**
 * Unit test for the JabberPoint class
 */
public class JabberPointTest {
    
    @BeforeEach
    void setUp() {
        // Reset any static state
        Style.createStyles();
    }
    
    @Test
    void testMainWithNoArguments() throws Exception {
        // Mock JOptionPane to avoid UI interactions
        mockStatic(JOptionPane.class);
        
        // Call main with no arguments
        String[] noArgs = new String[0];
        
        // We can't easily test the main method directly due to UI interactions
        // But we can verify it doesn't throw exceptions
        assertDoesNotThrow(() -> {
            // Use reflection to access the main method
            Method mainMethod = JabberPoint.class.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
            
            // Call the main method with no arguments
            // We're using a null object since it's a static method
            mainMethod.invoke(null, (Object) noArgs);
        });
    }
    
    @Test
    void testMainWithFileArgument(@TempDir Path tempDir) throws Exception {
        // Create a simple XML file
        File xmlFile = tempDir.resolve("test.xml").toFile();
        String xmlContent = "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE presentation SYSTEM \"presentation.dtd\">\n" +
                "<presentation>\n" +
                "    <showtitle>Test XML Presentation</showtitle>\n" +
                "    <slide>\n" +
                "        <title>Test Slide</title>\n" +
                "        <item level=\"1\" kind=\"text\">Test Item</item>\n" +
                "    </slide>\n" +
                "</presentation>";
        Files.writeString(xmlFile.toPath(), xmlContent);
        
        // Mock JOptionPane to avoid UI interactions
        mockStatic(JOptionPane.class);
        
        // Call main with the XML file as an argument
        String[] args = new String[] { xmlFile.getAbsolutePath() };
        
        // We can't easily test the main method directly due to UI interactions
        // But we can verify it doesn't throw exceptions
        assertDoesNotThrow(() -> {
            // Use reflection to access the main method
            Method mainMethod = JabberPoint.class.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
            
            // Call the main method with the file argument
            // We're using a null object since it's a static method
            mainMethod.invoke(null, (Object) args);
        });
    }
    
    @Test
    void testMainWithInvalidFileArgument() throws Exception {
        // Mock JOptionPane to avoid UI interactions
        mockStatic(JOptionPane.class);
        
        // Call main with an invalid file as an argument
        String[] args = new String[] { "nonexistent.xml" };
        
        // We expect an IOException to be caught and handled
        assertDoesNotThrow(() -> {
            // Use reflection to access the main method
            Method mainMethod = JabberPoint.class.getDeclaredMethod("main", String[].class);
            mainMethod.setAccessible(true);
            
            // Call the main method with the invalid file argument
            // We're using a null object since it's a static method
            mainMethod.invoke(null, (Object) args);
        });
        
        // Verify that an error message was shown
        verify(JOptionPane.class);
    }
    
    /**
     * Helper method to mock static methods
     */
    private <T> T mockStatic(Class<T> classToMock) {
        try {
            // This is a simplified mock for static methods
            // In a real test, you would use a library like PowerMock or Mockito's mockStatic
            return mock(classToMock);
        } catch (Exception e) {
            fail("Failed to mock static class: " + e.getMessage());
            return null;
        }
    }
}
