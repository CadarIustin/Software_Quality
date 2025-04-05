package jabberpoint.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        // Test different style levels
        Style titleStyle = darkTheme.getStyle(0);
        Style level1Style = darkTheme.getStyle(1);
        Style level2Style = darkTheme.getStyle(2);
        Style level3Style = darkTheme.getStyle(3);
        Style level4Style = darkTheme.getStyle(4);
        
        // Verify title style properties
        assertNotNull(titleStyle);
        assertEquals(Style.FontName.SANS_SERIF, titleStyle.getFontName());
        
        // Verify level properties decrease in size
        assertTrue(titleStyle.getFontSize() > level1Style.getFontSize());
        assertTrue(level1Style.getFontSize() > level2Style.getFontSize());
        assertTrue(level2Style.getFontSize() > level3Style.getFontSize());
        assertTrue(level3Style.getFontSize() > level4Style.getFontSize());
    }
    
    @Test
    void testDifferentThemes() {
        // Test that default and dark themes are different
        Style defaultTitleStyle = defaultTheme.getStyle(0);
        Style darkTitleStyle = darkTheme.getStyle(0);
        
        // They should have different colors
        assertNotEquals(defaultTitleStyle.getColor(), darkTitleStyle.getColor());
    }
    
    @Test
    void testOutOfBoundsLevels() {
        // Test negative level
        Style negativeStyle = defaultTheme.getStyle(-1);
        // Should return the default/fallback style
        assertNotNull(negativeStyle);
        
        // Test very high level
        Style highLevelStyle = defaultTheme.getStyle(100);
        // Should return the default/fallback style or the last defined style
        assertNotNull(highLevelStyle);
    }
}
