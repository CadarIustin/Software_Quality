package jabberpoint.util;

import java.awt.Color;
import java.awt.Font;
import jabberpoint.model.Style;

public class DarkTheme implements ThemeStrategy {
    private static final String FONTNAME = "Arial";
    
    @Override
    public void applyTheme() {
        Style.clearStyles();
        
        // Style for level 0 (title)
        Style.addStyle(0, new Style(0, 48, Color.ORANGE, FONTNAME, 48, Font.BOLD));
        
        // Style for level 1
        Style.addStyle(1, new Style(20, 36, Color.YELLOW, FONTNAME, 36, Font.BOLD));
        
        // Style for level 2
        Style.addStyle(2, new Style(50, 24, Color.WHITE, FONTNAME, 24, Font.BOLD));
        
        // Style for level 3
        Style.addStyle(3, new Style(70, 18, Color.LIGHT_GRAY, FONTNAME, 18, Font.BOLD));
        
        // Style for level 4
        Style.addStyle(4, new Style(90, 14, Color.LIGHT_GRAY, FONTNAME, 14, Font.PLAIN));
    }
    
    @Override
    public String getThemeName() {
        return "Dark";
    }
}
