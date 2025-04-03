package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * <p>The base component interface for the Composite pattern implementation of slide items</p>
 * <p>This interface defines the common operations for all slide items, both simple and composite.</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public interface SlideItemComponent {
    /**
     * Get the level of the slide item
     * @return the level
     */
    int getLevel();
    
    /**
     * Set the level of the slide item
     * @param level the new level
     */
    void setLevel(int level);
    
    /**
     * Get the bounding box of the slide item
     * @param g the graphics context
     * @param observer the image observer
     * @param scale the scale factor
     * @param style the style to use
     * @return the bounding box
     */
    Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style);
    
    /**
     * Draw the slide item
     * @param x the x position
     * @param y the y position
     * @param scale the scale factor
     * @param g the graphics context
     * @param style the style to use
     * @param observer the image observer
     */
    void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer);
}
