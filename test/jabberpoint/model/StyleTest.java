package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;

/**
 * Test class for Style
 */
public class StyleTest {
    
    private Style defaultStyle;
    private Style customStyle;
    
    @BeforeEach
    public void setUp() {
        Style.createStyles(); // Initialize the static styles
        defaultStyle = new Style();
        customStyle = new Style(20, 30, Color.BLUE, Style.FontName.SANS_SERIF.getName(), 24, Font.ITALIC);
    }
    
    @Test
    public void testDefaultConstructor() {
        assertEquals(10, defaultStyle.indent); // DEFAULT_INDENT is 10
        assertEquals(Color.BLACK, defaultStyle.getColor());
        assertEquals(Style.FontName.SANS_SERIF, defaultStyle.getFontName());
        assertEquals(24, defaultStyle.getFontSize());
    }
    
    @Test
    public void testCustomConstructor() {
        assertEquals(20, customStyle.indent);
        assertEquals(30, customStyle.leading);
        assertEquals(Color.BLUE, customStyle.getColor());
        assertEquals(Style.FontName.SANS_SERIF, customStyle.getFontName());
        assertEquals(24, customStyle.getFontSize());
    }
    
    @Test
    public void testGetFont() {
        Font font = customStyle.getFont(1.0f);
        assertNotNull(font);
        assertEquals(Style.FontName.SANS_SERIF.getName(), font.getFamily());
        assertEquals(24, font.getSize());
        assertEquals(Font.ITALIC, font.getStyle());
    }
    
    @Test
    public void testGetFontWithScaling() {
        Font font = customStyle.getFont(2.0f);
        assertNotNull(font);
        assertEquals(Style.FontName.SANS_SERIF.getName(), font.getFamily());
        assertEquals(48, font.getSize()); // 24 * 2.0
        assertEquals(Font.ITALIC, font.getStyle());
    }
    
    @Test
    public void testFontNameEnum() {
        assertEquals("Serif", Style.FontName.SERIF.getName());
        assertEquals("SansSerif", Style.FontName.SANS_SERIF.getName());
        assertEquals("Monospaced", Style.FontName.MONOSPACED.getName());
    }
    
    @Test
    public void testGetStyleByLevel() {
        // Test getting styles for different levels
        Style style0 = Style.getStyle(0);
        assertNotNull(style0);
        assertEquals(Color.RED, style0.getColor());
        
        Style style1 = Style.getStyle(1);
        assertNotNull(style1);
        assertEquals(Color.BLUE, style1.getColor());
        
        // Test getting style for a level that doesn't exist - should return default
        Style nonExistentStyle = Style.getStyle(999);
        assertNotNull(nonExistentStyle);
        assertEquals(Color.BLACK, nonExistentStyle.getColor());
    }
    
    @Test
    public void testAddAndClearStyles() {
        // Clear existing styles
        Style.clearStyles();
        
        // Add a new style
        Style newStyle = new Style(50, 16, Color.GREEN, Style.FontName.MONOSPACED.getName(), 16, Font.BOLD);
        Style.addStyle(5, newStyle);
        
        // Retrieve the added style
        Style retrievedStyle = Style.getStyle(5);
        assertNotNull(retrievedStyle);
        assertEquals(Color.GREEN, retrievedStyle.getColor());
        
        // Clear styles again
        Style.clearStyles();
        
        // After clearing, should return default style
        Style afterClear = Style.getStyle(5);
        assertNotNull(afterClear);
        assertEquals(Color.BLACK, afterClear.getColor());
    }
    
    @Test
    public void testIndentAndLeading() {
        assertEquals(20, customStyle.indent);
        assertEquals(30, customStyle.leading);
    }
}
