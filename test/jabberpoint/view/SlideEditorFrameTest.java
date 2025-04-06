package jabberpoint.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;

import jabberpoint.model.Presentation;

/**
 * Unit test for the SlideEditorFrame class
 */
public class SlideEditorFrameTest {
    
    private SlideEditorFrame editorFrame;
    private Presentation presentation;
    private Frame mockParentFrame;
    
    @BeforeEach
    void setUp() {
        // Create a real presentation
        presentation = new Presentation();
        presentation.setTitle("Test Presentation");
        
        // Create a mock parent frame
        mockParentFrame = mock(Frame.class);
        
        // Create a partial mock of SlideEditorFrame to avoid actual UI rendering
        editorFrame = spy(new SlideEditorFrame(mockParentFrame, presentation) {
            // Override methods that interact with the actual UI
            @Override
            public void setVisible(boolean b) {
                // Do nothing
            }
            
            @Override
            public void setLocationRelativeTo(java.awt.Component c) {
                // Do nothing, but record that it was called
                super.setLocationRelativeTo(null);
            }
            
            @Override
            public void addWindowListener(WindowListener l) {
                // Do nothing, but record that it was called
                super.addWindowListener(l);
            }
        });
    }
    
    @Test
    void testFrameInitialization() {
        // Verify the frame was initialized with the correct title
        assertEquals("JabberPoint - Slide Editor", editorFrame.getTitle());
        
        // Verify the editor panel was created and added to the content pane
        Container contentPane = editorFrame.getContentPane();
        Component centerComponent = ((BorderLayout)contentPane.getLayout()).getLayoutComponent(contentPane, BorderLayout.CENTER);
        assertNotNull(centerComponent);
        assertTrue(centerComponent instanceof SlideEditorPanel);
    }
    
    @Test
    void testDefaultConstructor() {
        // Create a new frame with the default constructor
        SlideEditorFrame defaultFrame = spy(new SlideEditorFrame(presentation) {
            @Override
            public void setVisible(boolean b) {
                // Do nothing
            }
        });
        
        // Verify the frame was initialized with the correct title
        assertEquals("JabberPoint - Slide Editor", defaultFrame.getTitle());
        
        // Verify the parent frame is null
        try {
            Field parentField = SlideEditorFrame.class.getDeclaredField("parentFrame");
            parentField.setAccessible(true);
            assertNull(parentField.get(defaultFrame));
        } catch (Exception e) {
            fail("Failed to access parentFrame field: " + e.getMessage());
        }
    }
    
    @Test
    void testParentFrameConstructor() {
        // Verify the parent frame was set correctly
        try {
            Field parentField = SlideEditorFrame.class.getDeclaredField("parentFrame");
            parentField.setAccessible(true);
            assertEquals(mockParentFrame, parentField.get(editorFrame));
        } catch (Exception e) {
            fail("Failed to access parentFrame field: " + e.getMessage());
        }
        
        // Verify setLocationRelativeTo was called
        verify(editorFrame).setLocationRelativeTo(any());
    }
    
    @Test
    void testWindowListenerAdded() {
        // Verify that a WindowListener was added
        verify(editorFrame).addWindowListener(any(WindowListener.class));
    }
}
