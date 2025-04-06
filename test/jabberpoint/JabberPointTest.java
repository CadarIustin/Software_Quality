package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

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
    void testMainWithNoArguments() {
        // Call main with no arguments
        String[] noArgs = new String[0];
        
        // We can't easily test the main method directly due to UI interactions in a headless environment
        // But we can verify it doesn't throw exceptions
        assertDoesNotThrow(() -> {
            JabberPoint.main(noArgs);
        });
    }
    
    @Test
    void testMainWithFileArgument(@TempDir Path tempDir) throws IOException {
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
        
        // Call main with the XML file as an argument
        String[] args = new String[] { xmlFile.getAbsolutePath() };
        
        // We can't easily test the main method directly due to UI interactions in a headless environment
        // But we can verify it doesn't throw exceptions when given a valid file
        assertDoesNotThrow(() -> {
            JabberPoint.main(args);
        });
    }
    
    @Test
    void testMainWithInvalidFileArgument() {
        // Call main with an invalid file as an argument
        String[] args = new String[] { "nonexistent.xml" };
        
        // We expect an IOException to be caught and handled without throwing
        assertDoesNotThrow(() -> {
            JabberPoint.main(args);
        });
    }
    
    @Test
    void testMainWithMultipleArguments(@TempDir Path tempDir) throws IOException {
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
        
        // Call main with multiple arguments (should only use the first one)
        String[] args = new String[] { xmlFile.getAbsolutePath(), "second.xml", "third.xml" };
        
        // We can't easily test the main method directly due to UI interactions in a headless environment
        // But we can verify it doesn't throw exceptions
        assertDoesNotThrow(() -> {
            JabberPoint.main(args);
        });
    }
    
    @Test
    void testMainWithHeadlessProperty() {
        // Set headless mode for testing
        System.setProperty("java.awt.headless", "true");
        
        try {
            // Call main with no arguments
            String[] noArgs = new String[0];
            
            // Verify it doesn't throw exceptions in headless mode
            assertDoesNotThrow(() -> {
                JabberPoint.main(noArgs);
            });
        } finally {
            // Reset the property
            System.setProperty("java.awt.headless", "false");
        }
    }
    
    @Test
    void testMainImplementationDetails() throws Exception {
        // This test verifies some implementation details without actually running the main method
        
        // Check that the main method exists with the correct signature
        Method mainMethod = JabberPoint.class.getDeclaredMethod("main", String[].class);
        assertNotNull(mainMethod);
        
        // Check that the JABVERSION constant has the expected value
        Field versionField = JabberPoint.class.getDeclaredField("JABVERSION");
        versionField.setAccessible(true);
        String version = (String) versionField.get(null);
        assertEquals("Jabberpoint 2.0", version);
        
        // Check that the WELCOME_MESSAGE constant exists and is not empty
        Field welcomeField = JabberPoint.class.getDeclaredField("WELCOME_MESSAGE");
        welcomeField.setAccessible(true);
        String welcomeMessage = (String) welcomeField.get(null);
        assertNotNull(welcomeMessage);
        assertFalse(welcomeMessage.isEmpty());
        assertTrue(welcomeMessage.contains("Welcome to JabberPoint"));
    }
}
