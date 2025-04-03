package jabberpoint;

import java.awt.Color;
import java.awt.Font;

/**
 * <p>Strategy interface for the Strategy pattern</p>
 * <p>This interface defines the behavior for different presentation themes</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public interface ThemeStrategy {
    /**
     * Get the color for the slide background
     * @return the background color
     */
    Color getBackgroundColor();
    
    /**
     * Get the color for the slide text
     * @return the text color
     */
    Color getTextColor();
    
    /**
     * Get the color for the slide title
     * @return the title color
     */
    Color getTitleColor();
    
    /**
     * Get the font for the slide text
     * @param level the level of the text
     * @return the font
     */
    Font getFont(int level);
    
    /**
     * Get the font size for the given level
     * @param level the level
     * @return the font size
     */
    int getFontSize(int level);
    
    /**
     * Get the theme name
     * @return the theme name
     */
    String getThemeName();
}
