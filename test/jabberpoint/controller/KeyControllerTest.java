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
        
        // Test Down Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        keyController.keyPressed(mockKeyEvent);
        
        // Test Enter key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        keyController.keyPressed(mockKeyEvent);
        
        // Test Right Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        keyController.keyPressed(mockKeyEvent);
        
        // Test Plus key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PLUS);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify nextSlide was called 5 times
        verify(mockPresentation, times(5)).nextSlide();
    }
    
    @Test
    void testPreviousSlideKeyPressed() {
        // Test Page Up key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_PAGE_UP);
        keyController.keyPressed(mockKeyEvent);
        
        // Test Up Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        keyController.keyPressed(mockKeyEvent);
        
        // Test '-' key (character '-')
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_MINUS);
        keyController.keyPressed(mockKeyEvent);
        
        // Test Left Arrow key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify previousSlide was called 4 times
        verify(mockPresentation, times(4)).previousSlide();
    }
    
    @Test
    void testExitKeyPressed() {
        // Create a testable KeyController subclass that overrides exit()
        TestableKeyController testableController = new TestableKeyController(mockPresentation);
        
        // Test 'q' key
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'q');
        testableController.keyPressed(mockKeyEvent);
        
        // Test 'Q' key (uppercase)
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'Q');
        testableController.keyPressed(mockKeyEvent);
        
        // Verify exit was called (should be true after either 'q' or 'Q')
        assertTrue(testableController.wasExitCalled(), "Exit should be called when 'q' or 'Q' is pressed");
        
        // Verify other methods were not called
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
    
    // Test-specific subclass that overrides the exit method
    private static class TestableKeyController extends KeyController {
        private boolean exitCalled = false;
        
        public TestableKeyController(Presentation presentation) {
            super(presentation);
        }
        
        @Override
        protected void exit() {
            exitCalled = true;
            // Don't call System.exit in tests
        }
        
        public boolean wasExitCalled() {
            return exitCalled;
        }
    }
}
