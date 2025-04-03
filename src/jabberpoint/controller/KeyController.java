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
                presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_LEFT:
                presentation.previousSlide();
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            default:
                break;
        }
    }
}
