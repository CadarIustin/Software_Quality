import jabberpoint.model.Observer;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;

/**
 * Interface for observers specifically interested in Presentation updates
 * This extends the general Observer interface to create a specific type
 * for the Presentation observer pattern implementation
 * 
 * @version 1.0 2025/04/06
 */
public interface PresentationObserver extends Observer {
    /**
     * Update method called when the observed presentation changes
     * 
     * @param presentation The current state of the presentation
     * @param slide The current slide being displayed
     */
    void update(Presentation presentation, Slide slide);
}
