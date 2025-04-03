package jabberpoint;

/**
 * <p>Subject interface for the Observer pattern</p>
 * <p>This interface is implemented by classes that want to notify observers of state changes</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public interface PresentationSubject {
    /**
     * Register an observer to receive notifications
     * @param observer the observer to register
     */
    void registerObserver(PresentationObserver observer);
    
    /**
     * Remove an observer from receiving notifications
     * @param observer the observer to remove
     */
    void removeObserver(PresentationObserver observer);
    
    /**
     * Notify all registered observers of a change
     */
    void notifyObservers();
}
