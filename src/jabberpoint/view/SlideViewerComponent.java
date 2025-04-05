package jabberpoint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.Observer;
import jabberpoint.util.TransitionManager;
import jabberpoint.util.ThemeStrategy;

/** 
 * JabberPoint SlideViewerComponent
 * This class represents the presentation viewer component.
 */
public class SlideViewerComponent extends JComponent implements Observer {
    private static final long serialVersionUID = 227L;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    private Slide slide;
    private Font labelFont = null;
    private Presentation presentation;
    private TransitionManager transitionManager;
    private boolean inTransition = false;
    private JFrame frame;
    private ThemeStrategy theme;

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        this.presentation = pres;
        this.labelFont = new Font("Dialog", Font.BOLD, 10);
        this.presentation.addObserver(this);
        this.slide = null;
        this.transitionManager = new TransitionManager(this);
        this.frame = frame;
        update(presentation, null);
    }

    public SlideViewerComponent(Presentation pres, ThemeStrategy theme) {
        this.presentation = pres;
        this.theme = theme;
        // Use the theme for styling
        if (theme != null) {
            this.labelFont = new Font(
                theme.getStyle(0).getFontName().toString(), 
                Font.BOLD, 
                theme.getStyle(0).getFontSize());
        } else {
            this.labelFont = new Font("Dialog", Font.BOLD, 10);
        }
        this.presentation.addObserver(this);
        this.slide = null;
        this.transitionManager = new TransitionManager(this);
        update(presentation, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    @Override
    public void update(Presentation presentation, Slide data) {
        this.presentation = presentation;
        Slide previousSlide = this.slide;
        this.slide = data;
        
        // Update frame title if available
        if (frame != null && presentation != null) {
            frame.setTitle(presentation.getTitle());
        }
        
        // Start transition if both slides are available and we're not already in a transition
        if (previousSlide != null && slide != null && !inTransition) {
            inTransition = true;
            transitionManager.startTransition(previousSlide, slide);
        }
        
        repaint();
    }
    
    public void startTransition() {
        inTransition = true;
        
        Slide fromSlide = this.slide;
        
        Slide toSlide = null;
        int currentNumber = presentation.getSlideNumber();
        if (currentNumber < presentation.getSize() - 1) {
            toSlide = presentation.getSlide(currentNumber + 1);
        } else {
            toSlide = fromSlide;
        }
        
        transitionManager.startTransition(fromSlide, toSlide);
    }

    public void stopTransition() {
        inTransition = false;
        repaint();
    }

    public boolean isInTransition() {
        return inTransition;
    }
    
    /**
     * Called by TransitionManager when a transition animation completes
     */
    public void finishTransition() {
        inTransition = false;
        repaint();
    }

    /**
     * Cycles to the next transition type
     */
    public void cycleTransitionType() {
        transitionManager.cycleTransition();
    }
    
    /**
     * Get the name of the current transition effect
     * @return The name of the current transition effect
     */
    public String getCurrentTransitionName() {
        return transitionManager.getCurrentTransitionName();
    }

    public void setSlide(Slide slide) {
        this.slide = slide;
        repaint();
    }

    public Slide getSlide() {
        return slide;
    }
    
    /**
     * Get the current presentation
     * @return The presentation being displayed
     */
    public Presentation getPresentation() {
        return presentation;
    }
    
    /**
     * Set a new theme for the slide viewer
     * @param theme The new theme to apply
     */
    public void setTheme(ThemeStrategy theme) {
        this.theme = theme;
        if (theme != null) {
            this.labelFont = new Font(
                theme.getStyle(0).getFontName().toString(), 
                Font.BOLD, 
                theme.getStyle(0).getFontSize());
        }
        repaint(); // Refresh the view with the new theme
    }

    @Override
    public void paintComponent(Graphics g) {
        if (theme != null) {
            // Use default background color since Style doesn't have background color
            g.setColor(Color.white);
        } else {
            g.setColor(Color.white);
        }
        g.fillRect(0, 0, getSize().width, getSize().height);
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        g.setFont(labelFont);
        if (theme != null) {
            g.setColor(theme.getStyle(0).getColor());
        } else {
            g.setColor(Color.black);
        }
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + 
                     " of " + presentation.getSize(), XPOS, YPOS);
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        
        // Just use the existing draw method - we'll apply theme styling elsewhere
        slide.draw(g, area, this);
    }
}
