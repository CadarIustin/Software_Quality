package jabberpoint.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import jabberpoint.model.Presentation;

public class KeyController extends KeyAdapter {
    private Presentation presentation;

    public KeyController(Presentation p) {
        presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_PLUS:  // Use KeyEvent.VK_PLUS instead of '+'
                presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_MINUS:  // Use KeyEvent.VK_MINUS instead of '-'
                presentation.previousSlide();
                break;
            case KeyEvent.VK_Q:
                exit();
                break;
            default:
                break;
        }
    }
    
    /**
     * Exit the application
     * This method can be overridden in tests to prevent actual system exit
     */
    protected void exit() {
        System.exit(0);
    }
}
