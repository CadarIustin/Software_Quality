package jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Presentation maintains the slides in a presentation.
 * Implements Observable pattern to notify views of changes.
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 2.0 2025/04/03 Cadarustin
 */
public class Presentation implements Observable {
    private String title; // title of the presentation
    private List<Slide> slides; // slides in the presentation
    private int currentSlideNumber; // the current slide number
    private List<Observer> observers; // observers to notify of changes

    /**
     * Default constructor
     */
    public Presentation() {
        this.title = "New Presentation";
        this.slides = new ArrayList<>();
        this.currentSlideNumber = -1;
        this.observers = new ArrayList<>();
    }

    /**
     * Get the number of slides
     * @return Number of slides
     */
    public int getSize() {
        return slides.size();
    }

    /**
     * Get the title of the presentation
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the presentation
     * @param title The new title
     */
    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    /**
     * Get the current slide number
     * @return The current slide number
     */
    public int getSlideNumber() {
        return currentSlideNumber;
    }

    /**
     * Set the current slide number and notify observers
     * @param number The new slide number
     */
    public void setSlideNumber(int number) {
        if (number < -1 || (slides.size() > 0 && number >= slides.size())) {
            throw new IllegalArgumentException("Invalid slide number: " + number);
        }
        currentSlideNumber = number;
        notifyObservers();
    }

    /**
     * Go to the previous slide
     */
    public void previousSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    /**
     * Go to the next slide
     */
    public void nextSlide() {
        if (currentSlideNumber < (slides.size() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    /**
     * Clear the presentation
     */
    public void clear() {
        slides = new ArrayList<>();
        setSlideNumber(-1);
    }

    /**
     * Add a slide to the presentation
     * @param slide The slide to add
     */
    public void addSlide(Slide slide) {
        slides.add(slide);
        notifyObservers();
    }

    /**
     * Remove a slide from the presentation
     * @param index The index of the slide to remove
     */
    public void removeSlide(int index) {
        if (index >= 0 && index < slides.size()) {
            slides.remove(index);
            
            // Adjust current slide number if necessary
            if (currentSlideNumber >= slides.size()) {
                setSlideNumber(slides.size() - 1);
            } else {
                notifyObservers();
            }
        }
    }

    /**
     * Get a slide by number
     * @param number The slide number
     * @return The slide, or null if the number is invalid
     */
    public Slide getSlide(int number) {
        if (number < 0 || number >= slides.size()) {
            return null;
        }
        return slides.get(number);
    }

    /**
     * Get the current slide
     * @return The current slide, or null if there is none
     */
    public Slide getCurrentSlide() {
        return getSlide(currentSlideNumber);
    }

    // Observer pattern implementation

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this, getCurrentSlide());
        }
    }

    /**
     * Exit the application
     * @param code The exit code
     */
    public void exit(int code) {
        System.exit(code);
    }
}
