package jabberpoint.model;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * The abstract class for an item on a slide.
 * All SlideItems have drawing functionality.
 * This is part of the Composite pattern implementation.
 * 
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 2.0 2025/04/03 Cadarustin
 */
public abstract class SlideItem {
    private int level = 0; // level of the slideitem

    public SlideItem(int lev) {
        level = lev;
    }

    public SlideItem() {
        this(0);
    }

    /**
     * Returns the level of the slide item
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the bounding box of the slide item
     * @param g Graphics object
     * @param observer ImageObserver for image loading
     * @param scale Scale factor for drawing
     * @param style Style to apply
     * @return Rectangle representing the bounding box
     */
    public abstract Rectangle getBoundingBox(Graphics g, 
            ImageObserver observer, float scale, Style style);

    /**
     * Draws the slide item
     * @param x X-coordinate to start drawing
     * @param y Y-coordinate to start drawing
     * @param scale Scale factor for drawing
     * @param g Graphics object
     * @param style Style to apply
     * @param observer ImageObserver for image loading
     */
    public abstract void draw(int x, int y, float scale, 
            Graphics g, Style style, ImageObserver observer);
}
