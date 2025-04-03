package jabberpoint.util;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class SlideTransition implements TransitionStrategy {
    private boolean slideLeft;
    
    public SlideTransition(boolean slideLeft) {
        this.slideLeft = slideLeft;
    }
    
    @Override
    public void performTransition(Graphics g, Rectangle area, BufferedImage fromImage, BufferedImage toImage, double progress, ImageObserver observer) {
        int offset = (int)(area.width * progress);
        
        if (slideLeft) {
            // Slide left transition (current slide moves left, new slide comes from right)
            g.drawImage(fromImage, area.x - offset, area.y, area.width, area.height, observer);
            g.drawImage(toImage, area.x + area.width - offset, area.y, area.width, area.height, observer);
        } else {
            // Slide right transition (current slide moves right, new slide comes from left)
            g.drawImage(fromImage, area.x + offset, area.y, area.width, area.height, observer);
            g.drawImage(toImage, area.x - area.width + offset, area.y, area.width, area.height, observer);
        }
    }
    
    @Override
    public String getTransitionName() {
        return slideLeft ? "Slide Left" : "Slide Right";
    }
}
