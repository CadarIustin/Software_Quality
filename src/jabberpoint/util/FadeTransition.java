package jabberpoint.util;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class FadeTransition implements TransitionStrategy {
    @Override
    public void performTransition(Graphics g, Rectangle area, BufferedImage fromImage, BufferedImage toImage, double progress, ImageObserver observer) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Draw the first image fully
        g2d.drawImage(fromImage, area.x, area.y, area.width, area.height, observer);
        
        // Draw the second image with increasing alpha
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)progress);
        g2d.setComposite(alphaComposite);
        g2d.drawImage(toImage, area.x, area.y, area.width, area.height, observer);
        
        // Restore the default composite
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
    
    @Override
    public String getTransitionName() {
        return "Fade";
    }
}
