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
        // Create a testable KeyController subclass that overrides exit()
        TestableKeyController testableController = new TestableKeyController(mockPresentation);
        
        // Test 'q' key
        when(mockKeyEvent.getKeyCode()).thenReturn((int)'q');
        testableController.keyPressed(mockKeyEvent);
        
        // Verify exit was called
        assertTrue(testableController.wasExitCalled());
        
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
