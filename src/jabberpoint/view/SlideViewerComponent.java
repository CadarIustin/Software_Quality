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
import jabberpoint.model.PresentationObserver;

/** 
 * JabberPoint SlideViewerComponent
 * This class represents the presentation viewer component.
 */
public class SlideViewerComponent extends JComponent implements PresentationObserver {
    private static final long serialVersionUID = 227L;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = Color.BLACK;

    private Slide slide;
    private Font labelFont;
    private Presentation presentation;
    private JFrame frame;

    public SlideViewerComponent(Presentation pres) {
        this.presentation = pres;
        this.labelFont = new Font("Dialog", Font.BOLD, 10);
        this.presentation.addObserver(this);
        this.slide = null;
        update(presentation, null);
    }

    public SlideViewerComponent(Presentation pres, JFrame frame) {
        this.presentation = pres;
        this.labelFont = new Font("Dialog", Font.BOLD, 10);
        this.presentation.addObserver(this);
        this.slide = null;
        this.frame = frame;
        update(presentation, null);
    }

    public Slide getSlide() {
        return slide;
    }

    @Override
    public void update(Presentation presentation, Slide slide) {
        if (this.presentation != presentation) {
            this.presentation = presentation;
        }
        
        this.slide = slide;
        
        // If no slide was provided, get the current slide from presentation
        if (this.slide == null && presentation.getSlideNumber() >= 0) {
            this.slide = presentation.getSlide(presentation.getSlideNumber());
        }
        
        repaint();
        
        if (frame != null) {
            frame.setTitle(presentation.getTitle());
        }
    }

    private Rectangle getSlideBounds() {
        return new Rectangle(0, 0, getWidth(), getHeight());
    }

    public void paintComponent(Graphics g) {
        if (presentation.getSlideNumber() < 0 || slide == null) {
            return;
        }
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(TEXT_COLOR);
        g.setFont(labelFont);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " + presentation.getSize(), XPOS, YPOS);

        slide.draw(g, getSlideBounds(), this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, 800);
    }
}
