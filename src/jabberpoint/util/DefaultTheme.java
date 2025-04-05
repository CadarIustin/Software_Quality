package jabberpoint.util;

import java.awt.Color;
import java.awt.Font;
import jabberpoint.model.Style;

public class DefaultTheme implements ThemeStrategy {
    private static final String FONTNAME = Style.FontName.SANS_SERIF.getName();
    
    @Override
    public void applyTheme() {
        // Create styles for each level
        Style.clearStyles();
        
        // Style for level 0 (title)
        Style.addStyle(0, new Style(0, 48, Color.RED, FONTNAME, 48, Font.BOLD));
        
        // Style for level 1
        Style.addStyle(1, new Style(20, 36, Color.BLUE, FONTNAME, 36, Font.BOLD));
        
        // Style for level 2
        Style.addStyle(2, new Style(50, 24, Color.BLACK, FONTNAME, 24, Font.BOLD));
        
        // Style for level 3
        Style.addStyle(3, new Style(70, 18, Color.BLACK, FONTNAME, 18, Font.BOLD));
        
        // Style for level 4
        Style.addStyle(4, new Style(90, 14, Color.BLACK, FONTNAME, 14, Font.PLAIN));
    }
    
    @Override
    public String getThemeName() {
        return "Default";
    }
    
    @Override
    public Style getStyle(int level) {
        // First ensure styles are applied
        if (Style.getStyle(level) == null) {
            applyTheme();
        }
        return Style.getStyle(level);
    }
}
