package jabberpoint.util;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import jabberpoint.model.Slide;

public interface TransitionStrategy {
    void performTransition(Graphics g, Rectangle area, BufferedImage fromImage, BufferedImage toImage, double progress, ImageObserver observer);
    String getTransitionName();
}
