package jabberpoint.model;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class BitmapItem extends SlideItem {
  private BufferedImage bufferedImage;
  private String imageName;
  
  protected static final String FILE = "File ";
  protected static final String NOTFOUND = " not found";

  public BitmapItem(int level, String name) {
    super(level);
    imageName = name;
    try {
      bufferedImage = ImageIO.read(new File(imageName));
    }
    catch (IOException e) {
      System.err.println(FILE + imageName + NOTFOUND);
    }
  }

  public BitmapItem() {
    this(0, null);
  }

  public String getName() {
    return imageName;
  }

  public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
    return new Rectangle((int) (myStyle.indent * scale), 0,
        (int) (bufferedImage.getWidth(observer) * scale),
        ((int) (myStyle.leading * scale)) + 
        (int) (bufferedImage.getHeight(observer) * scale));
  }

  public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
    int width = x + (int) (myStyle.indent * scale);
    int height = y + (int) (myStyle.leading * scale);
    g.drawImage(bufferedImage, width, height,(int) (bufferedImage.getWidth(observer)*scale),
              (int) (bufferedImage.getHeight(observer)*scale), observer);
  }

  public String toString() {
    return "BitmapItem[" + getLevel() + "," + imageName + "]";
  }
}
