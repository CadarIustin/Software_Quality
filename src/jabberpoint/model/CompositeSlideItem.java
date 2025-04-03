package jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * CompositeSlideItem represents a group of slide items.
 * This is the "Composite" part of the Composite design pattern.
 * It allows treating individual objects and compositions of objects uniformly.
 */
public class CompositeSlideItem extends SlideItem {
    
    private List<SlideItem> children = new ArrayList<>();
    private String name;

    /**
     * Constructor with level specification
     * @param level The level of this item
     * @param name The name of this composite item
     */
    public CompositeSlideItem(int level, String name) {
        super(level);
        this.name = name;
    }

    /**
     * Default constructor
     */
    public CompositeSlideItem() {
        this(0, "Group");
    }

    /**
     * Add a child SlideItem to this composite
     * @param item The item to add
     */
    public void add(SlideItem item) {
        children.add(item);
    }

    /**
     * Remove a child SlideItem from this composite
     * @param item The item to remove
     */
    public void remove(SlideItem item) {
        children.remove(item);
    }

    /**
     * Get the child at the specified index
     * @param index The index of the child to retrieve
     * @return The SlideItem at the specified index
     */
    public SlideItem getChild(int index) {
        return children.get(index);
    }

    /**
     * Get the number of children
     * @return The number of children
     */
    public int getSize() {
        return children.size();
    }

    /**
     * Calculate the bounding box containing all child items
     */
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        if (children.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }
        
        // Get the first child's bounding box as a starting point
        Rectangle boundingBox = children.get(0).getBoundingBox(g, observer, scale, style);
        
        // Combine with other children's bounding boxes
        for (int i = 1; i < children.size(); i++) {
            Rectangle childBox = children.get(i).getBoundingBox(g, observer, scale, style);
            boundingBox = boundingBox.union(childBox);
        }
        
        return boundingBox;
    }

    /**
     * Draw all child items
     */
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        for (SlideItem item : children) {
            item.draw(x, y, scale, g, style, observer);
        }
    }
    
    /**
     * Get the name of this composite
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set the name of this composite
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "CompositeSlideItem[" + getLevel() + "," + name + ", " + children.size() + " items]";
    }
}
