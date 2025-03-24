package jabberpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
    void testGetAttributedText() {
        // Since AttributedString is not easy to test directly, we can at least verify it doesn't throw an exception
        assertDoesNotThrow(() -> {
            textItem.getAttributedText(new Style(0, 0, 0, 0, 0), 0, 0);
        }, "getAttributedText should not throw exceptions");
    }
    
    @Test
    void testBuildString() {
        StringBuilder builder = new StringBuilder();
        textItem.buildString(builder, "  ");
        
        String expected = "  " + TEXT;
        assertEquals(expected, builder.toString(), "buildString should append the correct text with indentation");
    }
}
