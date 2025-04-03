package jabberpoint.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;

import jabberpoint.model.Observer;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.util.TransitionManager;

/** 
 * JabberPoint SlideViewerComponent
 * This class represents the presentation viewer component.
 */
public class SlideViewerComponent extends JComponent implements Observer {
    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTSIZE = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    private Slide slide;
    private Font labelFont = null;
    private Presentation presentation;
    private TransitionManager transitionManager;
    private boolean inTransition = false;

    public SlideViewerComponent(Presentation pres) {
        this.presentation = pres;
        this.labelFont = new Font(FONTNAME, FONTSTYLE, FONTSIZE);
        this.presentation.addObserver(this);
        this.slide = null;
        this.transitionManager = new TransitionManager(this);
        update();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Slide.WIDTH, Slide.HEIGHT);
    }

    @Override
    public void update() {
        if (presentation.getCurrentSlide() != slide) {
            Slide previousSlide = slide;
            slide = presentation.getCurrentSlide();
            
            // Start transition if both slides are available and we're not already in a transition
            if (previousSlide != null && slide != null && !inTransition) {
                inTransition = true;
                transitionManager.startTransition(previousSlide, slide);
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(BGCOLOR);
        g.fillRect(0, 0, getSize().width, getSize().height);
        
        if (inTransition && transitionManager.isTransitionActive()) {
            // Draw the transition animation
            Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
            transitionManager.drawTransition(g, area, this);
            return;
        }
        
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        
        g.setFont(labelFont);
        g.setColor(COLOR);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                    presentation.getSize(), XPOS, YPOS);
        
        Rectangle area = new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
        slide.draw(g, area, this);
    }
    
    public TransitionManager getTransitionManager() {
        return transitionManager;
    }
    
    public void finishTransition() {
        inTransition = false;
        repaint();
    }
    
    public void cycleTransitionType() {
        transitionManager.cycleTransition();
    }
    
    public String getCurrentTransitionName() {
        return transitionManager.getCurrentTransitionName();
    }
}
