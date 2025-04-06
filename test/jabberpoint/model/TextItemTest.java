package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.image.ImageObserver;
import java.text.AttributedString;

/**
 * Unit test for the TextItem class
 */
public class TextItemTest {
    
    private TextItem textItem;
    private Style style;
    private Graphics mockGraphics;
    private ImageObserver mockObserver;
    
    @BeforeEach
    void setUp() {
        textItem = new TextItem(1, "Test Text");
        style = new Style(10, 20, Color.BLACK, Style.FontName.SANS_SERIF.getName(), 12, Font.PLAIN);
        mockGraphics = mock(Graphics.class);
        mockObserver = mock(ImageObserver.class);
        
        // Setup mock graphics for a Graphics2D object
        Graphics2D mockG2d = mock(Graphics2D.class);
        FontRenderContext mockFrc = mock(FontRenderContext.class);
        when(mockGraphics.create()).thenReturn(mockG2d);
        when(mockG2d.getFontRenderContext()).thenReturn(mockFrc);
    }
    
    @Test
    void testConstructorWithParams() {
        TextItem item = new TextItem(2, "Some Text");
        assertEquals(2, item.getLevel());
        assertEquals("Some Text", item.getText());
    }
    
    @Test
    void testDefaultConstructor() {
        TextItem defaultItem = new TextItem();
        assertEquals(0, defaultItem.getLevel());
        assertEquals("No Text Given", defaultItem.getText());
    }
    
    @Test
    void testGetText() {
        assertEquals("Test Text", textItem.getText());
        
        // Test with null text
        TextItem nullTextItem = new TextItem(1, null);
        assertEquals("", nullTextItem.getText(), "Null text should return empty string");
    }
    
    @Test
    void testGetAttributedString() {
        AttributedString attrString = textItem.getAttributedString(style, 1.0f);
        assertNotNull(attrString, "AttributedString should not be null");
    }
    
    @Test
    void testGetBoundingBox() {
        // We can only test the public getBoundingBox method
        Rectangle boundingBox = textItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, style);
        
        // We can't verify exact dimensions due to the private getLayouts method,
        // but we can verify that a non-null rectangle is returned
        assertNotNull(boundingBox);
        assertEquals(10, boundingBox.x, "X position should match style indent");
    }
    
    @Test
    void testDrawWithEmptyText() {
        TextItem emptyTextItem = new TextItem(1, "");
        // Should not throw exceptions when drawing empty text
        assertDoesNotThrow(() -> {
            emptyTextItem.draw(10, 20, 1.0f, mockGraphics, style, mockObserver);
        });
    }
    
    @Test
    void testDraw() {
        // Test that drawing doesn't throw exceptions
        assertDoesNotThrow(() -> {
            textItem.draw(10, 20, 1.0f, mockGraphics, style, mockObserver);
        });
    }
    
    @Test
    void testDrawWithNullText() {
        TextItem nullTextItem = new TextItem(1, null);
        // Drawing with null text should not throw exceptions
        assertDoesNotThrow(() -> {
            nullTextItem.draw(10, 20, 1.0f, mockGraphics, style, mockObserver);
        });
    }
    
    @Test
    void testToString() {
        assertEquals("TextItem[1,Test Text]", textItem.toString());
    }
}
