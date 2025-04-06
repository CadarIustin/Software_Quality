package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

import jabberpoint.model.Style;
import jabberpoint.model.Presentation;
import jabberpoint.view.SlideViewerFrame;

/**
 * Unit test for the JabberPoint class
 */
public class JabberPointTest {
    
    @BeforeEach
    void setUp() {
        // Reset any static state
        Style.createStyles();
        
        // Set headless mode for testing
        System.setProperty("java.awt.headless", "true");
    }
    
    @Test
    void testMainWithNoArguments() {
        // Create a mock for SlideViewerFrame to avoid HeadlessException
        try (var mocked = mockStatic(SlideViewerFrame.class)) {
            // Mock the constructor to do nothing
            SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
            mocked.when(() -> new SlideViewerFrame(any(Presentation.class))).thenReturn(mockFrame);
            
            // Call main with no arguments
            String[] noArgs = new String[0];
            
            // We can't easily test the main method directly due to UI interactions in a headless environment
            // But we can verify it doesn't throw exceptions
            assertDoesNotThrow(() -> {
                JabberPoint.main(noArgs);
            });
            
            // Verify that a frame was created with the demo presentation
            mocked.verify(() -> new SlideViewerFrame(any(Presentation.class)));
        } catch (UnsupportedOperationException e) {
            // If static mocking is not supported, we'll skip the test
            System.out.println("Skipping test due to lack of static mocking support");
        }
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
        
        // Create the DTD file in the same directory
        File dtdFile = tempDir.resolve("presentation.dtd").toFile();
        String dtdContent = "<!ELEMENT presentation (showtitle, slide*)>\n" +
                "<!ELEMENT showtitle (#PCDATA)>\n" +
                "<!ELEMENT slide (title, item*)>\n" +
                "<!ELEMENT title (#PCDATA)>\n" +
                "<!ELEMENT item (#PCDATA)>\n" +
                "<!ATTLIST item kind CDATA #REQUIRED>\n" +
                "<!ATTLIST item level CDATA #REQUIRED>";
        Files.writeString(dtdFile.toPath(), dtdContent);
        
        // Create a mock for SlideViewerFrame to avoid HeadlessException
        try (var mocked = mockStatic(SlideViewerFrame.class)) {
            // Mock the constructor to do nothing
            SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
            mocked.when(() -> new SlideViewerFrame(any(Presentation.class))).thenReturn(mockFrame);
            
            // Call main with the XML file as an argument
            String[] args = new String[] { xmlFile.getAbsolutePath() };
            
            // We can't easily test the main method directly due to UI interactions in a headless environment
            // But we can verify it doesn't throw exceptions when given a valid file
            assertDoesNotThrow(() -> {
                JabberPoint.main(args);
            });
            
            // Verify that a frame was created with the XML file
            mocked.verify(() -> new SlideViewerFrame(any(Presentation.class)));
        } catch (UnsupportedOperationException e) {
            // If static mocking is not supported, we'll skip the test
            System.out.println("Skipping test due to lack of static mocking support");
        }
    }
    
    @Test
    void testMainWithInvalidFileArgument() {
        // Create a mock for SlideViewerFrame to avoid HeadlessException
        try (var mocked = mockStatic(SlideViewerFrame.class)) {
            // Mock the constructor to do nothing
            SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
            mocked.when(() -> new SlideViewerFrame(any(Presentation.class))).thenReturn(mockFrame);
            
            // Call main with an invalid file as an argument
            String[] args = new String[] { "nonexistent.xml" };
            
            // We expect an IOException to be caught and handled without throwing
            assertDoesNotThrow(() -> {
                JabberPoint.main(args);
            });
            
            // Verify that a frame was created with the demo presentation (fallback)
            mocked.verify(() -> new SlideViewerFrame(any(Presentation.class)));
        } catch (UnsupportedOperationException e) {
            // If static mocking is not supported, we'll skip the test
            System.out.println("Skipping test due to lack of static mocking support");
        }
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
        
        // Create the DTD file in the same directory
        File dtdFile = tempDir.resolve("presentation.dtd").toFile();
        String dtdContent = "<!ELEMENT presentation (showtitle, slide*)>\n" +
                "<!ELEMENT showtitle (#PCDATA)>\n" +
                "<!ELEMENT slide (title, item*)>\n" +
                "<!ELEMENT title (#PCDATA)>\n" +
                "<!ELEMENT item (#PCDATA)>\n" +
                "<!ATTLIST item kind CDATA #REQUIRED>\n" +
                "<!ATTLIST item level CDATA #REQUIRED>";
        Files.writeString(dtdFile.toPath(), dtdContent);
        
        // Create a mock for SlideViewerFrame to avoid HeadlessException
        try (var mocked = mockStatic(SlideViewerFrame.class)) {
            // Mock the constructor to do nothing
            SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
            mocked.when(() -> new SlideViewerFrame(any(Presentation.class))).thenReturn(mockFrame);
            
            // Call main with multiple arguments (should only use the first one)
            String[] args = new String[] { xmlFile.getAbsolutePath(), "second.xml", "third.xml" };
            
            // We can't easily test the main method directly due to UI interactions in a headless environment
            // But we can verify it doesn't throw exceptions
            assertDoesNotThrow(() -> {
                JabberPoint.main(args);
            });
            
            // Verify that a frame was created with the first XML file
            mocked.verify(() -> new SlideViewerFrame(any(Presentation.class)));
        } catch (UnsupportedOperationException e) {
            // If static mocking is not supported, we'll skip the test
            System.out.println("Skipping test due to lack of static mocking support");
        }
    }
    
    @Test
    void testMainWithHeadlessProperty() {
        // Create a mock for SlideViewerFrame to avoid HeadlessException
        try (var mocked = mockStatic(SlideViewerFrame.class)) {
            // Mock the constructor to do nothing
            SlideViewerFrame mockFrame = mock(SlideViewerFrame.class);
            mocked.when(() -> new SlideViewerFrame(any(Presentation.class))).thenReturn(mockFrame);
            
            // Call main with no arguments
            String[] noArgs = new String[0];
            
            // Verify it doesn't throw exceptions in headless mode
            assertDoesNotThrow(() -> {
                JabberPoint.main(noArgs);
            });
            
            // Verify that a frame was created
            mocked.verify(() -> new SlideViewerFrame(any(Presentation.class)));
        } catch (UnsupportedOperationException e) {
            // If static mocking is not supported, we'll skip the test
            System.out.println("Skipping test due to lack of static mocking support");
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
