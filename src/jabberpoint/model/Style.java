package jabberpoint.model;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

/**
 * Style class for presentation elements
 * Style settings for levels of slide items
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
public class Style {
    private static final Map<Integer, Style> STYLES = new HashMap<>();
    private static final String FONTNAME = "Helvetica";
    
    private static Style defaultStyle;
    private static final int DEFAULT_FONT_SIZE = 24;
    private static final int DEFAULT_LEADING = 20;
    private static final int DEFAULT_INDENT = 10;
    
    public enum FontName {
        SERIF("Serif"), 
        SANS_SERIF("SansSerif"), 
        MONOSPACED("Monospaced");
        
        private final String fontName;
        
        FontName(String fontName) {
            this.fontName = fontName;
        }
        
        public String getName() {
            return fontName;
        }
    }
    
    public int indent;
    public int leading;
    public Color color;
    private Font font;
    private int fontSize;
    private String fontName;
    
    /**
     * Create a new style
     * @param indent Indentation in pixels
     * @param leading Line spacing (between base lines) in pixels
     * @param color Text color
     * @param fontName Font name
     * @param fontSize Font size (in points)
     * @param style Font style (Font.PLAIN, Font.BOLD, Font.ITALIC)
     */
    public Style(int indent, int leading, Color color, String fontName, int fontSize, int style) {
        this.indent = indent;
        this.leading = leading;
        this.color = color;
        this.fontSize = fontSize;
        this.fontName = fontName;
        this.font = new Font(fontName, style, fontSize);
    }
    
    /**
     * Create a default style
     */
    public Style() {
        this(DEFAULT_INDENT, DEFAULT_LEADING, Color.BLACK, FONTNAME, DEFAULT_FONT_SIZE, Font.BOLD);
    }
    
    /**
     * Get the font for a certain scale
     * @param scale The scale to apply
     * @return The scaled font
     */
    public Font getFont(float scale) {
        return font.deriveFont(fontSize * scale);
    }
    
    /**
     * Get the font size
     * @return The font size
     */
    public int getFontSize() {
        return fontSize;
    }
    
    /**
     * Get the color
     * @return The color
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Get the font name
     * @return The font name as a FontName enum
     */
    public FontName getFontName() {
        // Convert the string font name to FontName enum
        for (FontName name : FontName.values()) {
            if (name.getName().equalsIgnoreCase(fontName)) {
                return name;
            }
        }
        // Default to SANS_SERIF if no match
        return FontName.SANS_SERIF;
    }
    
    /**
     * Get a style for a given level
     * @param level The level
     * @return The style corresponding to the level
     */
    public static Style getStyle(int level) {
        if (STYLES.containsKey(level)) {
            return STYLES.get(level);
        }
        return defaultStyle;
    }
    
    /**
     * Initialize the styles for all levels
     */
    public static void createStyles() {
        defaultStyle = new Style();
        clearStyles();
        
        // Style for level 0 (title)
        addStyle(0, new Style(0, 48, Color.RED, FONTNAME, 48, Font.BOLD));
        
        // Style for level 1
        addStyle(1, new Style(20, 36, Color.BLUE, FONTNAME, 36, Font.BOLD));
        
        // Style for level 2
        addStyle(2, new Style(50, 24, Color.BLACK, FONTNAME, 24, Font.BOLD));
        
        // Style for level 3
        addStyle(3, new Style(70, 18, Color.BLACK, FONTNAME, 18, Font.BOLD));
        
        // Style for level 4
        addStyle(4, new Style(90, 14, Color.BLACK, FONTNAME, 14, Font.PLAIN));
    }
    
    /**
     * Clear all styles
     */
    public static void clearStyles() {
        STYLES.clear();
    }
    
    /**
     * Add a style for a given level
     * @param level The level
     * @param style The style to add
     */
    public static void addStyle(int level, Style style) {
        STYLES.put(level, style);
    }
}
