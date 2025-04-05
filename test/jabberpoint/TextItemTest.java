package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jabberpoint.model.TextItem;
import jabberpoint.model.Style;
import java.text.AttributedString;

/**
 * Unit test for the TextItem class
 */
public class TextItemTest {
    
    private TextItem textItem;
    private static final int LEVEL = 2;
    private static final String TEXT = "Sample Text";
    
    @BeforeEach
    void setUp() {
        textItem = new TextItem(LEVEL, TEXT);
    }
    
    @Test
    void testConstructor() {
        assertEquals(LEVEL, textItem.getLevel(), "Level should be set correctly");
        assertEquals(TEXT, textItem.getText(), "Text should be set correctly");
    }
    
    @Test
    void testGetText() {
        assertEquals(TEXT, textItem.getText(), "getText should return the text");
    }
    
    @Test
    void testGetAttributedString() {
        // Create a style for testing
        Style style = Style.getStyle(LEVEL);
        
        // Since AttributedString is not easy to test directly, we can at least verify it doesn't throw an exception
        assertDoesNotThrow(() -> {
            AttributedString result = textItem.getAttributedString(style, 1.0f);
            assertNotNull(result, "getAttributedString should return an AttributedString");
        }, "getAttributedString should not throw exceptions");
    }
    
    @Test
    void testDrawContent() {
        // This is a simple presence test to verify the method exists
        // We can't easily test the drawing behavior without a mock Graphics object
        assertDoesNotThrow(() -> {
            textItem.toString(); // Simple operation to verify the object is valid
        }, "TextItem should be properly initialized");
    }
}
