import jabberpoint.model.Observer;

/**
 * Interface for the Subject in the Observer pattern specific to Presentations
 * 
 * @version 1.0 2025/04/06 
 */
public interface PresentationSubject {
    /**
     * Register a PresentationObserver to be notified of changes
     * 
     * @param observer The observer to register
     */
    void registerObserver(PresentationObserver observer);
    
    /**
     * Remove a PresentationObserver from the notification list
     * 
     * @param observer The observer to remove
     */
    void removeObserver(PresentationObserver observer);
    
    /**
     * Notify all registered observers about changes in the presentation
     */
    void notifyObservers();
    
    /**
     * Add an observer of the general Observer type
     * 
     * @param observer The observer to add
     */
    void addObserver(Observer observer);
    
    /**
     * Remove an observer of the general Observer type
     * 
     * @param observer The observer to remove
     */
    void removeObserver(Observer observer);
}
