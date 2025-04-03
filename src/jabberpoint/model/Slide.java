package jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * A slide in a presentation. 
 * This class has drawing functionality.
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
public class Slide {
    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;
    
    private String title; // title is saved separately
    private List<SlideItem> items; // slide items are saved in a List

    /**
     * Default constructor
     */
    public Slide() {
        items = new ArrayList<>();
    }

    /**
     * Add a slide item
     * @param anItem The item to add
     */
    public void append(SlideItem anItem) {
        items.add(anItem);
    }

    /**
     * Get the title of the slide
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Change the title of the slide
     * @param newTitle The new title
     */
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    /**
     * Create a TextItem from a String, and add it to the slide
     * @param level The level of the text item
     * @param message The text message
     */
    public void append(int level, String message) {
        append(new TextItem(level, message));
    }

    /**
     * Get the SlideItem at the specified index
     * @param number The index of the item
     * @return The SlideItem at the index
     */
    public SlideItem getSlideItem(int number) {
        if (number < 0 || number >= items.size()) {
            return null;
        }
        return items.get(number);
    }

    /**
     * Get all SlideItems
     * @return List of all SlideItems
     */
    public List<SlideItem> getSlideItems() {
        return items;
    }

    /**
     * Get the number of items on the slide
     * @return The number of items
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Draw the slide
     * @param g Graphics object
     * @param area Rectangle representing the drawing area
     * @param view The image observer
     */
    public void draw(Graphics g, Rectangle area, ImageObserver view) {
        float scale = getScale(area);
        int y = area.y;
        
        // Title is handled separately
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;
        
        // Draw each item
        for (SlideItem item : items) {
            style = Style.getStyle(item.getLevel());
            item.draw(area.x, y, scale, g, style, view);
            y += item.getBoundingBox(g, view, scale, style).height;
        }
    }

    /**
     * Calculate the scale for drawing
     * @param area The area to draw in
     * @return The scale factor
     */
    private float getScale(Rectangle area) {
        return Math.min(((float)area.width) / ((float)WIDTH), ((float)area.height) / ((float)HEIGHT));
    }
}
