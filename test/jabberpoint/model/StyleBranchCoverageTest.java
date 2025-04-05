package jabberpoint.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;

/**
 * This test class is specifically designed to improve branch coverage
 * by targeting conditional logic in the Style class.
 */
public class StyleBranchCoverageTest {
    
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
        // We can't clear styles directly, so let's work with existing styles
        
        // Get styles that should already exist after previous tests
        Style style0 = Style.getStyle(0);
        assertNotNull(style0);
        
        // Get the same style again (different branch)
        Style style0Again = Style.getStyle(0);
        assertSame(style0, style0Again); // Should be the same object
        
        // Get a different style level
        Style style1 = Style.getStyle(1);
        assertNotNull(style1);
        
        // Get a style with very high index (another branch)
        Style styleHigh = Style.getStyle(100);
        assertNotNull(styleHigh);
    }
    
    @Test
    void testDerivedFontBranches() {
        Style style = new Style();
        
        // Test with different scale values
        Font normalFont = style.getFont(1.0f);
        assertNotNull(normalFont);
        
        Font largerFont = style.getFont(2.0f);
        assertNotNull(largerFont);
        
        // Font size should be proportional to scale
        assertEquals(normalFont.getSize() * 2, largerFont.getSize());
    }
}
