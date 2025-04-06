package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;

/**
 * This test class is specifically designed to improve branch coverage
 * by targeting conditional logic in the Style class.
 */
public class StyleBranchCoverageTest {
    
    @BeforeEach
    void setUp() {
        // Initialize styles before tests and ensure defaultStyle is set
        Style.clearStyles();  // Clear first to ensure clean state
        Style.createStyles(); // This should create the default style and initialize the styles map
    }
    
    @Test
    void testStyleConstructorBranches() {
        // Create styles with different parameters to exercise branches
        Style defaultStyle = new Style();
        assertNotNull(defaultStyle);
        
        // Test explicit constructor with various parameters
        Style customStyle = new Style(20, 30, Color.RED, 
                                      Style.FontName.SANS_SERIF.getName(), 
                                      40, Font.BOLD);
        
        assertNotNull(customStyle);
        assertEquals(20, customStyle.indent);
        assertEquals(30, customStyle.leading);
        assertEquals(Color.RED, customStyle.getColor());
        assertEquals(40, customStyle.getFontSize());
    }
    
    @Test
    void testGetStyleBranches() {
        // Make sure styles are properly initialized first
        Style.createStyles();
        
        // Get styles that should already exist after initialization
        Style style0 = Style.getStyle(0);
        assertNotNull(style0, "Style for level 0 should not be null");
        
        // Get the same style again (different branch)
        Style style0Again = Style.getStyle(0);
        assertSame(style0, style0Again, "Getting the same style twice should return the same object");
        
        // Get a different style level
        Style style1 = Style.getStyle(1);
        assertNotNull(style1, "Style for level 1 should not be null");
        
        // Get a style with very high index (another branch - should return default style)
        Style styleHigh = Style.getStyle(100);
        assertNotNull(styleHigh, "Style for high level should return default style");
        assertEquals(Style.getStyle(100).indent, new Style().indent, "High level style should have default indent");
    }
    
    @Test
    void testFontNameEnum() {
        // Test the FontName enum
        assertEquals("Serif", Style.FontName.SERIF.getName());
        assertEquals("SansSerif", Style.FontName.SANS_SERIF.getName());
        assertEquals("Monospaced", Style.FontName.MONOSPACED.getName());
        
        // Test constructor used in enum
        for (Style.FontName fontName : Style.FontName.values()) {
            assertNotNull(fontName.getName());
        }
        
        // Test getFontName method
        Style serifStyle = new Style(20, 30, Color.BLACK, 
                                   Style.FontName.SERIF.getName(), 
                                   40, Font.PLAIN);
        assertEquals(Style.FontName.SERIF, serifStyle.getFontName());
        
        Style sansStyle = new Style(20, 30, Color.BLACK, 
                                   Style.FontName.SANS_SERIF.getName(), 
                                   40, Font.PLAIN);
        assertEquals(Style.FontName.SANS_SERIF, sansStyle.getFontName());
        
        // Test with non-matching font name (should default to SANS_SERIF)
        Style unknownStyle = new Style(20, 30, Color.BLACK, 
                                     "UnknownFont", 
                                     40, Font.PLAIN);
        assertEquals(Style.FontName.SANS_SERIF, unknownStyle.getFontName());
    }
    
    @Test
    void testGetFontWithScaling() {
        Style style = new Style(20, 30, Color.BLACK, 
                              Style.FontName.SERIF.getName(), 
                              40, Font.BOLD);
        
        // Test scaling
        Font normalFont = style.getFont(1.0f);
        Font largerFont = style.getFont(2.0f);
        
        // Font size should be proportional to scale
        assertEquals(normalFont.getSize() * 2, largerFont.getSize());
    }
}
