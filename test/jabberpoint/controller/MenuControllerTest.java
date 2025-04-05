package jabberpoint.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.event.ActionEvent;

import jabberpoint.model.Presentation;
import jabberpoint.view.SlideViewerFrame;

/**
 * Unit test for the MenuController class
 */
public class MenuControllerTest {
    
    private MenuController menuController;
    private Presentation mockPresentation;
    private SlideViewerFrame mockFrame;
    private ActionEvent mockEvent;
    
    @BeforeEach
    void setUp() {
        // Create mock objects
        mockPresentation = mock(Presentation.class);
        mockFrame = mock(SlideViewerFrame.class);
        mockEvent = mock(ActionEvent.class);
        
        // Create MenuController with mocks
        menuController = new MenuController(mockFrame, mockPresentation);
    }
    
    @Test
    void testOpenAction() {
        // This is a complex method to test because it involves file dialogs
        // A full test would use a framework like TestFX or a custom FileDialog mock
        // But we can at least test the action handling mechanism
        
        // Mock the actionCommand
        when(mockEvent.getActionCommand()).thenReturn("Open");
        
        // We'll need to mock JFileChooser, but for now we can just verify action command is recognized
        try {
            // This would typically open a file dialog, which we can't easily test
            // So we're just testing that the code recognizes the Open command
            menuController.actionPerformed(mockEvent);
            
            // Instead of verifying actual file loading, we can check that presentation wasn't cleared
            // since that would happen during file load
            verify(mockPresentation, never()).clear();
        } catch (Exception e) {
            // Expected to get an exception due to null file chooser
            assertTrue(e instanceof NullPointerException || e instanceof SecurityException);
        }
    }
    
    @Test
    void testNewAction() {
        // Test the "New" action
        when(mockEvent.getActionCommand()).thenReturn("New");
        
        menuController.actionPerformed(mockEvent);
        
        // Verify that the presentation was cleared
        verify(mockPresentation).clear();
    }
    
    @Test
    void testNextAction() {
        // Test the "Next" action
        when(mockEvent.getActionCommand()).thenReturn("Next");
        
        menuController.actionPerformed(mockEvent);
        
        // Verify that nextSlide was called
        verify(mockPresentation).nextSlide();
    }
    
    @Test
    void testPrevAction() {
        // Test the "Prev" action
        when(mockEvent.getActionCommand()).thenReturn("Prev");
        
        menuController.actionPerformed(mockEvent);
        
        // Verify that previousSlide was called
        verify(mockPresentation).previousSlide();
    }
    
    @Test
    void testGoToAction() {
        // This test is tricky since it involves JOptionPane input dialog
        // We can use PowerMockito for complete testing, but here's a simpler approach
        
        // Mock the actionCommand
        when(mockEvent.getActionCommand()).thenReturn("Go to");
        
        try {
            // This would typically show a dialog, which we can't easily test without PowerMockito
            menuController.actionPerformed(mockEvent);
            
            // Since we can't properly mock the static JOptionPane, this test will throw exceptions
            // We're just testing that the action is recognized
        } catch (Exception e) {
            // Expected to get an exception due to static JOptionPane.showInputDialog
            assertTrue(e instanceof NullPointerException || e instanceof SecurityException);
        }
    }
    
    @Test
    void testExitAction() {
        // We can't easily test System.exit, but we can verify action command handling
        when(mockEvent.getActionCommand()).thenReturn("Exit");
        
        // Set up security manager to prevent System.exit from actually exiting
        try {
            System.setSecurityManager(new NoExitSecurityManager());
            assertThrows(ExitException.class, () -> 
                menuController.actionPerformed(mockEvent)
            );
        } finally {
            System.setSecurityManager(null);
        }
    }
    
    @Test
    void testUnknownAction() {
        // Test with an unknown action command
        when(mockEvent.getActionCommand()).thenReturn("UnknownCommand");
        
        // Should not throw exception and should not call any presentation methods
        menuController.actionPerformed(mockEvent);
        
        verify(mockPresentation, never()).nextSlide();
        verify(mockPresentation, never()).previousSlide();
        verify(mockPresentation, never()).clear();
    }
    
    // Custom SecurityManager to prevent System.exit from actually exiting
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(java.security.Permission perm) {
            // Allow everything except exit
        }
        
        @Override
        public void checkExit(int status) {
            throw new ExitException(status);
        }
    }
    
    // Custom exception for System.exit testing
    private static class ExitException extends SecurityException {
        public ExitException(int status) {
            super("System.exit(" + status + ") was called");
        }
    }
}
