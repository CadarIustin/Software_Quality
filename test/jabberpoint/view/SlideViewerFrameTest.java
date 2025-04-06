package jabberpoint.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.MenuBar;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;

import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;
import jabberpoint.model.Presentation;

/**
 * Unit test for the SlideViewerFrame class
 */
public class SlideViewerFrameTest {
    
    private SlideViewerFrame frame;
    private Presentation presentation;
    
    @BeforeEach
    void setUp() {
        // Create a real presentation
        presentation = new Presentation();
        
        // Create a test slide
        presentation.addSlide(new jabberpoint.model.Slide());
        
        // Create a partial mock of SlideViewerFrame to avoid actual UI rendering
        frame = spy(new SlideViewerFrame("Test Frame", presentation) {
            // Override methods that interact with the actual UI
            @Override
            public void setVisible(boolean b) {
                // Do nothing
            }
            
            @Override
            public void addWindowListener(WindowListener l) {
                // Do nothing, but record that it was called
                super.addWindowListener(l);
            }
            
            @Override
            public void setMenuBar(MenuBar mb) {
                // Do nothing, but record that it was called
                super.setMenuBar(mb);
            }
            
            @Override
            public void addKeyListener(KeyListener l) {
                // Do nothing, but record that it was called
                super.addKeyListener(l);
            }
        });
    }
    
    @Test
    void testFrameInitialization() {
        // Verify the frame was initialized with the correct title
        assertEquals("Test Frame", frame.getTitle());
        
        // Verify the SlideViewerComponent was created
        assertNotNull(frame.getSlideViewerComponent());
    }
    
    @Test
    void testDefaultConstructor() {
        // Create a new frame with the default constructor
        SlideViewerFrame defaultFrame = spy(new SlideViewerFrame(presentation) {
            @Override
            public void setVisible(boolean b) {
                // Do nothing
            }
        });
        
        // Verify the frame was initialized with the default title
        assertEquals("Jabberpoint 2.0", defaultFrame.getTitle());
        
        // Verify the SlideViewerComponent was created
        assertNotNull(defaultFrame.getSlideViewerComponent());
    }
    
    @Test
    void testKeyListenerAdded() {
        // Verify that a KeyController was added as a key listener
        verify(frame).addKeyListener(any(KeyController.class));
    }
    
    @Test
    void testMenuBarAdded() {
        // Verify that a MenuController was set as the menu bar
        verify(frame).setMenuBar(any(MenuController.class));
    }
    
    @Test
    void testWindowListenerAdded() {
        // Verify that a WindowListener was added
        verify(frame).addWindowListener(any(WindowListener.class));
    }
    
    @Test
    void testContentPaneSetup() {
        // Get the content pane
        Container contentPane = frame.getContentPane();
        
        // Verify the layout is BorderLayout
        assertTrue(contentPane.getLayout() instanceof BorderLayout);
        
        // Verify the SlideViewerComponent is in the center
        assertEquals(frame.getSlideViewerComponent(), 
                     ((BorderLayout)contentPane.getLayout()).getLayoutComponent(contentPane, BorderLayout.CENTER));
        
        // Verify there's a panel in the south position (navigation instructions)
        assertNotNull(((BorderLayout)contentPane.getLayout()).getLayoutComponent(contentPane, BorderLayout.SOUTH));
    }
}
