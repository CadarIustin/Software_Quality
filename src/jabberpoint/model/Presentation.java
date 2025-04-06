package jabberpoint.model;

import java.util.ArrayList;
import java.util.List;

public class Presentation implements Observable {
    private String title;
    private List<Slide> slides;
    private int currentSlideNumber;
    private List<Observer> observers;

    public Presentation() {
        this.title = "New Presentation";
        this.slides = new ArrayList<>();
        this.currentSlideNumber = 0; // Initialize to 0 instead of -1 to fix test failures
        this.observers = new ArrayList<>();
    }

    public int getSize() {
        return slides.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyObservers();
    }

    public int getSlideNumber() {
        return currentSlideNumber;
    }

    public void setSlideNumber(int number) {
        if (number < -1 || (slides.size() > 0 && number >= slides.size())) {
            throw new IllegalArgumentException("Invalid slide number: " + number);
        }
        currentSlideNumber = number;
        notifyObservers();
    }

    public void previousSlide() {
        if (currentSlideNumber > 0) {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    public void nextSlide() {
        if (currentSlideNumber < (slides.size() - 1)) {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    public void clear() {
        slides = new ArrayList<>();
        setSlideNumber(0); // Changed from -1 to 0 to be consistent with constructor
    }

    public void addSlide(Slide slide) {
        slides.add(slide);
        notifyObservers();
    }

    public void removeSlide(int index) {
        if (index >= 0 && index < slides.size()) {
            slides.remove(index);
            
            if (currentSlideNumber >= slides.size()) {
                setSlideNumber(slides.size() - 1);
            } else {
                notifyObservers();
            }
        }
    }

    public Slide getSlide(int number) {
        if (number < 0 || number >= slides.size()) {
            return null;
        }
        return slides.get(number);
    }

    public Slide getCurrentSlide() {
        return getSlide(currentSlideNumber);
    }

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

    public void exit(int code) {
        System.exit(code);
    }
}
