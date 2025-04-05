import java.util.ArrayList;
import java.util.List;

import jabberpoint.model.Observer;
import jabberpoint.view.SlideViewerComponent;

/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2025/04/03 Iustin Cadar (Modified to implement PresentationSubject for Observer pattern)
 */

public class Presentation implements PresentationSubject {
	private String showTitle; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = 0; // the slidenummer of the current Slide
	private List<PresentationObserver> observers = new ArrayList<>(); // list of observers

	public Presentation() {
		clear();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		// Add slideViewerComponent as an observer if it implements PresentationObserver
		if (slideViewerComponent instanceof PresentationObserver) {
			registerObserver((PresentationObserver) slideViewerComponent);
		}
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
		notifyObservers(); // Notify observers when title changes
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		// Add slideViewerComponent as an observer if it implements PresentationObserver
		if (slideViewerComponent instanceof PresentationObserver) {
			registerObserver((PresentationObserver) slideViewerComponent);
		}
	}

	// give the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// change the current slide number and signal it to the window
	public void setSlideNumber(int number) {
		currentSlideNumber = number;
		notifyObservers(); // Notify observers when slide changes
	}

	// go to the previous slide unless your at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	// go to the next slide unless your at the end of the presentation.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
		notifyObservers(); // Notify observers when a slide is added
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return (Slide)showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
	
	// Observer pattern methods
	
	@Override
	public void registerObserver(PresentationObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	@Override
	public void removeObserver(PresentationObserver observer) {
		observers.remove(observer);
	}
	
	@Override
	public void notifyObservers() {
		Slide currentSlide = getCurrentSlide();
		for (PresentationObserver observer : observers) {
			// Create a model.Presentation and model.Slide for the Observer interface
			jabberpoint.model.Presentation modelPresentation = createModelPresentation();
			jabberpoint.model.Slide modelSlide = null;
			if (currentSlide != null) {
				modelSlide = createModelSlide(currentSlide);
			}
			
			observer.update(modelPresentation, modelSlide);
		}
	}

	// Helper methods to convert between different types
	private jabberpoint.model.Presentation createModelPresentation() {
		jabberpoint.model.Presentation modelPres = new jabberpoint.model.Presentation();
		modelPres.setTitle(this.showTitle);
		return modelPres;
	}
	
	private jabberpoint.model.Slide createModelSlide(Slide slide) {
		// Create a model.Slide from our Slide
		jabberpoint.model.Slide modelSlide = new jabberpoint.model.Slide();
		modelSlide.setTitle(slide.getTitle());
		// Copy any necessary properties
		return modelSlide;
	}

	// Model Observer pattern implementation
	@Override
	public void addObserver(Observer observer) {
		if (observer instanceof PresentationObserver) {
			registerObserver((PresentationObserver) observer);
		}
	}

	@Override
	public void removeObserver(Observer observer) {
		if (observer instanceof PresentationObserver) {
			removeObserver((PresentationObserver) observer);
		}
	}
}
