package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import jabberpoint.model.Style;

/**
 * Unit test for the ThemeStrategy class and its implementations
 */
public class ThemeStrategyTest {
    
    private ThemeStrategy defaultTheme;
    private ThemeStrategy darkTheme;
    
    @BeforeEach
    void setUp() {
        defaultTheme = new DefaultTheme();
        darkTheme = new DarkTheme();
        
        // Clear any existing styles by reinitializing the themes
        defaultTheme.applyTheme();
    }
    
    @Test
    void testDefaultThemeStyles() {
        // Test different style levels
        Style titleStyle = defaultTheme.getStyle(0);
        Style level1Style = defaultTheme.getStyle(1);
        Style level2Style = defaultTheme.getStyle(2);
        Style level3Style = defaultTheme.getStyle(3);
        Style level4Style = defaultTheme.getStyle(4);
        
        // Verify title style properties
        assertNotNull(titleStyle);
        assertEquals(Style.FontName.SANS_SERIF, titleStyle.getFontName());
        assertTrue(titleStyle.getFontSize() > 30); // Title should be large
        
        // Verify level properties decrease in size
        assertTrue(titleStyle.getFontSize() > level1Style.getFontSize());
        assertTrue(level1Style.getFontSize() > level2Style.getFontSize());
        assertTrue(level2Style.getFontSize() > level3Style.getFontSize());
        assertTrue(level3Style.getFontSize() > level4Style.getFontSize());
    }
    
    @Test
    void testDarkThemeStyles() {
        // Apply dark theme
        darkTheme.applyTheme();
        
        // Test different style levels
        Style titleStyle = darkTheme.getStyle(0);
        Style level1Style = darkTheme.getStyle(1);
        Style level2Style = darkTheme.getStyle(2);
        Style level3Style = darkTheme.getStyle(3);
        
        // Verify title style properties
        assertNotNull(titleStyle);
        assertEquals(Style.FontName.SANS_SERIF, titleStyle.getFontName());
        
        // Verify level properties decrease in size
        assertTrue(titleStyle.getFontSize() > level1Style.getFontSize());
        assertTrue(level1Style.getFontSize() > level2Style.getFontSize());
        assertTrue(level2Style.getFontSize() > level3Style.getFontSize());
        
        // Verify color is not black (should be light color for dark theme)
        assertNotEquals(Color.BLACK, titleStyle.getColor());
    }
    
    @Test
    void testNonExistentStyle() {
        // Test very high style level (not explicitly defined)
        int veryHighLevel = 10;
        Style highLevelStyle = defaultTheme.getStyle(veryHighLevel);
        
        // Should return the default/fallback style or the last defined style
        assertNotNull(highLevelStyle);
    }
    
    @Test
    void testApplyTheme() {
        // Apply default theme
        defaultTheme.applyTheme();
        
        // Get styles for different levels and verify they exist
        Style style0 = Style.getStyle(0);
        Style style1 = Style.getStyle(1);
        Style style2 = Style.getStyle(2);
        Style style3 = Style.getStyle(3);
        Style style4 = Style.getStyle(4);
        
        // Verify styles were created with correct properties
        assertNotNull(style0);
        assertNotNull(style1);
        assertNotNull(style2);
        assertNotNull(style3);
        assertNotNull(style4);
        
        // Verify font names were set correctly (from memory: using FontName.SANS_SERIF)
        assertEquals(Style.FontName.SANS_SERIF, style0.getFontName());
        assertEquals(Style.FontName.SANS_SERIF, style1.getFontName());
        
        // Now apply dark theme and verify it changes the styles
        darkTheme.applyTheme();
        
        Style darkStyle0 = Style.getStyle(0);
        Style darkStyle1 = Style.getStyle(1);
        
        // Verify dark theme styles were applied
        assertNotNull(darkStyle0);
        assertNotNull(darkStyle1);
        assertEquals(Style.FontName.SANS_SERIF, darkStyle0.getFontName());
        assertEquals(Style.FontName.SANS_SERIF, darkStyle1.getFontName());
        
        // Verify the colors are different in dark theme
        assertNotEquals(Color.BLACK, darkStyle0.getColor());
    }
    
    @Test
    void testStyleFontNameEnum() {
        // Test all values in the FontName enum
        assertEquals("Serif", Style.FontName.SERIF.getName());
        assertEquals("SansSerif", Style.FontName.SANS_SERIF.getName());
        assertEquals("Monospaced", Style.FontName.MONOSPACED.getName());
    }
    
    @Test
    void testStyleConstructor() {
        // Test creating a style with specific parameters
        Style customStyle = new Style(12, 14, Color.BLUE, Style.FontName.MONOSPACED.getName(), 16, Font.BOLD);
        
        // Verify the style was created with correct properties
        assertEquals(12, customStyle.indent);
        assertEquals(14, customStyle.leading);
        assertEquals(Color.BLUE, customStyle.getColor());
        assertEquals(16, customStyle.getFontSize());
        
        // Test font scale functionality
        Font scaledFont = customStyle.getFont(1.5f);
        assertNotNull(scaledFont);
        assertEquals(24, scaledFont.getSize()); // 16 * 1.5 = 24
    }
    
    @Test
    void testThemeStrategyGetStyle() {
        // Test the getStyle method added to ThemeStrategy interface
        // This ensures styles are applied if they don't exist yet
        
        // Apply default theme
        defaultTheme.applyTheme();
        
        // Get style - should use the theme's style implementation
        Style style2 = defaultTheme.getStyle(2);
        Style style3 = defaultTheme.getStyle(3);
        
        // Verify the styles were created correctly
        assertNotNull(style2);
        assertNotNull(style3);
        assertTrue(style2.getFontSize() > style3.getFontSize());
        
        // Switch theme and verify we get different styles
        darkTheme.applyTheme();
        Style darkStyle2 = darkTheme.getStyle(2);
        
        assertNotNull(darkStyle2);
        // Dark theme should have different color than default theme
        assertNotEquals(style2.getColor(), darkStyle2.getColor());
    }
}
