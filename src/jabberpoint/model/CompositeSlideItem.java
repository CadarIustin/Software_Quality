package jabberpoint.model;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class CompositeSlideItem extends SlideItem {
    
    private List<SlideItem> children = new ArrayList<>();
    private String name;

    public CompositeSlideItem(int level, String name) {
        super(level);
        this.name = name;
    }

    public CompositeSlideItem() {
        this(0, "Group");
    }

    public void add(SlideItem item) {
        children.add(item);
    }

    public void remove(SlideItem item) {
        children.remove(item);
    }

    public SlideItem getChild(int index) {
        return children.get(index);
    }

    public int getSize() {
        return children.size();
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        if (children.isEmpty()) {
            return new Rectangle(0, 0, 0, 0);
        }
        
        Rectangle boundingBox = children.get(0).getBoundingBox(g, observer, scale, style);
        
        for (int i = 1; i < children.size(); i++) {
            Rectangle childBox = children.get(i).getBoundingBox(g, observer, scale, style);
            boundingBox = boundingBox.union(childBox);
        }
        
        return boundingBox;
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        for (SlideItem item : children) {
            item.draw(x, y, scale, g, style, observer);
        }
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "CompositeSlideItem[" + getLevel() + "," + name + ", " + children.size() + " items]";
    }
}
