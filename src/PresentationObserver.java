import jabberpoint.model.Observer;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;

/**
 * <p>Interface for the Observer pattern</p>
 * <p>This interface is implemented by classes that want to be notified of changes in the presentation</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public interface PresentationObserver extends Observer {
    /**
     * Update method called when the observed subject changes
     * @param presentation the presentation that has changed
     * @param currentSlide the current slide being displayed
     */
    @Override
    void update(Presentation presentation, Slide currentSlide);
}
