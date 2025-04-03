package jabberpoint.util;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

import jabberpoint.model.Slide;
import jabberpoint.view.SlideViewerComponent;

public class TransitionManager {
    private static final int TRANSITION_DURATION = 500; // milliseconds
    private static final int TRANSITION_STEPS = 20;
    
    private SlideViewerComponent viewerComponent;
    private TransitionStrategy currentTransition;
    private BufferedImage fromImage;
    private BufferedImage toImage;
    private Timer transitionTimer;
    private int currentStep;
    private List<TransitionStrategy> availableTransitions;
    private int currentTransitionIndex = 0;
    
    public TransitionManager(SlideViewerComponent viewer) {
        this.viewerComponent = viewer;
        
        // Initialize available transitions
        availableTransitions = new ArrayList<>();
        availableTransitions.add(new FadeTransition());
        availableTransitions.add(new SlideTransition(true));
        availableTransitions.add(new SlideTransition(false));
        
        // Set default transition
        currentTransition = availableTransitions.get(0);
    }
    
    public void startTransition(Slide fromSlide, Slide toSlide) {
        if (transitionTimer != null && transitionTimer.isRunning()) {
            transitionTimer.stop();
        }
        
        // Create snapshots of the slides
        Rectangle area = new Rectangle(0, 0, viewerComponent.getWidth(), viewerComponent.getHeight());
        fromImage = new BufferedImage(Math.max(1, viewerComponent.getWidth()), 
                                     Math.max(1, viewerComponent.getHeight()), 
                                     BufferedImage.TYPE_INT_ARGB);
        toImage = new BufferedImage(Math.max(1, viewerComponent.getWidth()), 
                                   Math.max(1, viewerComponent.getHeight()), 
                                   BufferedImage.TYPE_INT_ARGB);
        
        if (fromSlide != null) {
            fromSlide.draw(fromImage.createGraphics(), area, viewerComponent);
        }
        
        if (toSlide != null) {
            toSlide.draw(toImage.createGraphics(), area, viewerComponent);
        }
        
        // Start transition animation
        currentStep = 0;
        
        transitionTimer = new Timer(TRANSITION_DURATION / TRANSITION_STEPS, e -> {
            currentStep++;
            
            if (currentStep >= TRANSITION_STEPS) {
                // End of transition
                transitionTimer.stop();
                viewerComponent.finishTransition();
            } else {
                // Continue transition
                viewerComponent.repaint();
            }
        });
        
        transitionTimer.start();
    }
    
    public void drawTransition(Graphics g, Rectangle area, ImageObserver observer) {
        if (fromImage != null && toImage != null) {
            double progress = (double) currentStep / TRANSITION_STEPS;
            currentTransition.performTransition(g, area, fromImage, toImage, progress, observer);
        }
    }
    
    public boolean isTransitionActive() {
        return transitionTimer != null && transitionTimer.isRunning();
    }
    
    public void cycleTransition() {
        currentTransitionIndex = (currentTransitionIndex + 1) % availableTransitions.size();
        currentTransition = availableTransitions.get(currentTransitionIndex);
    }
    
    public String getCurrentTransitionName() {
        return currentTransition.getTransitionName();
    }
}
