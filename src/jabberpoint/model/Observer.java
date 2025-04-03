package jabberpoint.model;

/**
 * Observer interface for the Observer pattern.
 * Classes implementing this interface can receive updates from Observable objects.
 */
public interface Observer {
    /**
     * This method is called when the observed object changes
     * @param presentation The presentation that has changed
     * @param slide The current slide being displayed
     */
    void update(Presentation presentation, Slide slide);
}
