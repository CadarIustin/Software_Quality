package jabberpoint.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.ImageObserver;

public class ShapeItem extends SlideItem {
    public enum ShapeType {
        RECTANGLE,
        CIRCLE,
        LINE,
        TRIANGLE
    }
    
    private ShapeType shapeType;
    private int width;
    private int height;
    
    public ShapeItem(int level, ShapeType shapeType, int width, int height) {
        super(level);
        this.shapeType = shapeType;
        this.width = width;
        this.height = height;
    }
    
    public ShapeItem() {
        this(0, ShapeType.RECTANGLE, 100, 100);
    }
    
    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style style) {
        return new Rectangle(
            (int) (style.indent * scale),
            0,
            (int) (width * scale),
            (int) (height * scale)
        );
    }
    
    @Override
    public void draw(int x, int y, float scale, Graphics g, Style style, ImageObserver observer) {
        int xPosition = x + (int) (style.indent * scale);
        int yPosition = y + (int) (style.leading * scale);
        int scaledWidth = (int) (width * scale);
        int scaledHeight = (int) (height * scale);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(style.color);
        g2d.setStroke(new BasicStroke(2));
        
        switch (shapeType) {
            case RECTANGLE:
                g2d.drawRect(xPosition, yPosition, scaledWidth, scaledHeight);
                break;
                
            case CIRCLE:
                g2d.drawOval(xPosition, yPosition, scaledWidth, scaledHeight);
                break;
                
            case LINE:
                g2d.drawLine(xPosition, yPosition, xPosition + scaledWidth, yPosition + scaledHeight);
                break;
                
            case TRIANGLE:
                int[] xPoints = {xPosition, xPosition + scaledWidth / 2, xPosition + scaledWidth};
                int[] yPoints = {yPosition + scaledHeight, yPosition, yPosition + scaledHeight};
                g2d.drawPolygon(xPoints, yPoints, 3);
                break;
        }
    }
    
    public ShapeType getShapeType() {
        return shapeType;
    }
    
    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    @Override
    public String toString() {
        return "ShapeItem[" + getLevel() + "," + shapeType + "," + width + "x" + height + "]";
    }
}
