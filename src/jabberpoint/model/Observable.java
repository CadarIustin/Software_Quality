package jabberpoint.model;

/**
 * Observable interface for the Observer pattern.
 * Classes implementing this interface can be observed by Observer objects.
 */
public interface Observable {
    /**
     * Registers an observer to receive updates
     * @param observer The observer to be added
     */
    void addObserver(Observer observer);
    
    /**
     * Removes an observer from the notification list
     * @param observer The observer to be removed
     */
    void removeObserver(Observer observer);
    
    /**
     * Notifies all registered observers about a change
     */
    void notifyObservers();
}
