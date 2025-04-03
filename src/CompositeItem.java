package jabberpoint;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A composite item that can contain multiple slide items</p>
 * <p>This is the Composite part of the Composite pattern</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public class CompositeItem extends SlideItem {
    private List<SlideItemComponent> items;
    
    /**
     * Create a new composite item with the given level
     * @param level the level of the item
     */
    public CompositeItem(int level) {
        super(level);
        this.items = new ArrayList<>();
    }
    
    /**
     * Create a new composite item with level 0
     */
    public CompositeItem() {
        this(0);
    }
    
    /**
     * Add an item to this composite
     * @param item the item to add
     */
    public void addItem(SlideItemComponent item) {
        items.add(item);
    }
    
    /**
     * Remove an item from this composite
     * @param item the item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean removeItem(SlideItemComponent item) {
        return items.remove(item);
    }
    
    /**
     * Get all the child items in this composite
     * @return the list of child items
     */
    public List<SlideItemComponent> getItems() {
        return new ArrayList<>(items);
    }
    
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        if (items.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }
        
        // Get the first item's bounding box as the starting point
        Rectangle bounds = items.get(0).getBoundingBox(g, observer, scale, style);
        
        // Merge all other bounding boxes
        for (int i = 1; i < items.size(); i++) {
            Rectangle itemBounds = items.get(i).getBoundingBox(g, observer, scale, style);
            bounds = bounds.union(itemBounds);
        }
        
        return bounds;
    }
    
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        // Draw all contained items
        for (SlideItemComponent item : items) {
            item.draw(x, y, scale, g, style, observer);
        }
    }
}
