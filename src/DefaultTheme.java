import java.awt.Color;
import java.awt.Font;

/**
 * <p>Default theme implementation for the Strategy pattern</p>
 * <p>This class provides the default styling for presentations</p>
 * @author Iustin Cadar
 * @version 1.0 2025/04/03
 */
public class DefaultTheme implements ThemeStrategy {
    private static final String FONTNAME = "Arial";
    private static final Color BGCOLOR = Color.white;
    private static final Color TEXTCOLOR = Color.black;
    private static final Color TITLECOLOR = Color.blue;
    
    @Override
    public Color getBackgroundColor() {
        return BGCOLOR;
    }

    @Override
    public Color getTextColor() {
        return TEXTCOLOR;
    }

    @Override
    public Color getTitleColor() {
        return TITLECOLOR;
    }

    @Override
    public Font getFont(int level) {
        int style = level == 0 ? Font.BOLD : Font.PLAIN;
        return new Font(FONTNAME, style, getFontSize(level));
    }

    @Override
    public int getFontSize(int level) {
        switch (level) {
            case 0: return 48;  // Title
            case 1: return 40;  // Heading 1
            case 2: return 36;  // Heading 2
            case 3: return 32;  // Heading 3
            case 4: return 28;  // Heading 4
            default: return 24; // Body text
        }
    }

    @Override
    public String getThemeName() {
        return "Default Theme";
    }
}
