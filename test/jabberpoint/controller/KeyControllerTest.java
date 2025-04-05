package jabberpoint.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.event.KeyEvent;
import jabberpoint.model.Presentation;

/**
 * Unit test for the KeyController class
 */
public class KeyControllerTest {
    
    private KeyController keyController;
    private Presentation mockPresentation;
    private KeyEvent mockKeyEvent;
    
    @BeforeEach
    void setUp() {
        // Create mock objects
        mockPresentation = mock(Presentation.class);
        mockKeyEvent = mock(KeyEvent.class);
        
        // Create KeyController with mock Presentation
        keyController = new KeyController(mockPresentation);
    }
    
    @Test
    void testNextSlideKeyPressed() {
        // Test Page Down key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_DOWN);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).nextSlide();
        
        // Test Down Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation, times(2)).nextSlide();
        
        // Test Enter key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation, times(3)).nextSlide();
        
        // Test '+' key (character '+')
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'+');
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation, times(4)).nextSlide();
    }
    
    @Test
    void testPreviousSlideKeyPressed() {
        // Test Page Up key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_UP);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation).previousSlide();
        
        // Test Up Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation, times(2)).previousSlide();
        
        // Test '-' key (character '-')
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'-');
        keyController.keyPressed(mockKeyEvent);
        verify(mockPresentation, times(3)).previousSlide();
    }
    
    @Test
    void testExitKeyPressed() {
        // We can't easily test System.exit, but we can at least verify that
        // other methods are not called
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'q');
        
        // This will fail with SecurityException if System.exit is actually called
        // We'd need a SecurityManager to fully test this properly
        try {
            System.setSecurityManager(new NoExitSecurityManager());
            assertThrows(ExitException.class, () -> {
                keyController.keyPressed(mockKeyEvent);
            });
        } finally {
            System.setSecurityManager(null);
        }
        
        verify(mockPresentation, never()).nextSlide();
        verify(mockPresentation, never()).previousSlide();
    }
    
    @Test
    void testOtherKeyPressed() {
        // Test some other key that doesn't trigger any action
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_A);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify no methods were called on the presentation
        verify(mockPresentation, never()).nextSlide();
        verify(mockPresentation, never()).previousSlide();
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
        private final int status;
        
        public ExitException(int status) {
            super("System.exit(" + status + ") was called");
            this.status = status;
        }
        
        public int getStatus() {
            return status;
        }
    }
}
