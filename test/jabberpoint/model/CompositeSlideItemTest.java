package jabberpoint.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * Unit test for the CompositeSlideItem class
 */
public class CompositeSlideItemTest {
    
    private CompositeSlideItem compositeItem;
    private SlideItem mockChild1;
    private SlideItem mockChild2;
    private Graphics mockGraphics;
    private ImageObserver mockObserver;
    private Style mockStyle;
    
    @BeforeEach
    public void setUp() {
        // Create a composite item with level 1 and name "Test Group"
        compositeItem = new CompositeSlideItem(1, "Test Group");
        
        // Create mock child items
        mockChild1 = mock(SlideItem.class);
        mockChild2 = mock(SlideItem.class);
        
        // Create mock graphics, observer, and style
        mockGraphics = mock(Graphics.class);
        mockObserver = mock(ImageObserver.class);
        mockStyle = mock(Style.class);
    }
    
    @Test
    public void testConstructorWithParams() {
        // Test constructor with parameters
        CompositeSlideItem item = new CompositeSlideItem(2, "Named Group");
        assertEquals(2, item.getLevel());
        assertEquals("Named Group", item.getName());
        assertEquals(0, item.getSize());
    }
    
    @Test
    public void testDefaultConstructor() {
        // Test default constructor
        CompositeSlideItem item = new CompositeSlideItem();
        assertEquals(0, item.getLevel());
        assertEquals("Group", item.getName());
        assertEquals(0, item.getSize());
    }
    
    @Test
    public void testAddAndGetChild() {
        // Add child items
        compositeItem.add(mockChild1);
        compositeItem.add(mockChild2);
        
        // Verify size and children
        assertEquals(2, compositeItem.getSize());
        assertSame(mockChild1, compositeItem.getChild(0));
        assertSame(mockChild2, compositeItem.getChild(1));
    }
    
    @Test
    public void testRemove() {
        // Add child items
        compositeItem.add(mockChild1);
        compositeItem.add(mockChild2);
        assertEquals(2, compositeItem.getSize());
        
        // Remove one child
        compositeItem.remove(mockChild1);
        
        // Verify size and remaining child
        assertEquals(1, compositeItem.getSize());
        assertSame(mockChild2, compositeItem.getChild(0));
    }
    
    @Test
    public void testGetBoundingBoxWithNoChildren() {
        // Test with no children
        Rectangle emptyBox = compositeItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle);
        
        // Should return an empty rectangle
        assertNotNull(emptyBox);
        assertEquals(0, emptyBox.x);
        assertEquals(0, emptyBox.y);
        assertEquals(0, emptyBox.width);
        assertEquals(0, emptyBox.height);
    }
    
    @Test
    public void testGetBoundingBoxWithChildren() {
        // Add mock children with specific bounding boxes
        Rectangle box1 = new Rectangle(10, 10, 100, 50);
        Rectangle box2 = new Rectangle(50, 20, 150, 80);
        
        when(mockChild1.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle)).thenReturn(box1);
        when(mockChild2.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle)).thenReturn(box2);
        
        compositeItem.add(mockChild1);
        compositeItem.add(mockChild2);
        
        // Get the bounding box
        Rectangle boundingBox = compositeItem.getBoundingBox(mockGraphics, mockObserver, 1.0f, mockStyle);
        
        // Should be the union of the child bounding boxes
        assertNotNull(boundingBox);
        assertEquals(10, boundingBox.x);
        assertEquals(10, boundingBox.y);
        assertEquals(190, boundingBox.width);  // 10 + 150 + 30 (overlap)
        assertEquals(90, boundingBox.height);  // 10 + 80
    }
    
    @Test
    public void testDraw() {
        // Add mock children
        compositeItem.add(mockChild1);
        compositeItem.add(mockChild2);
        
        // Call draw
        compositeItem.draw(100, 200, 1.5f, mockGraphics, mockStyle, mockObserver);
        
        // Verify draw was called on each child with the same parameters
        verify(mockChild1).draw(100, 200, 1.5f, mockGraphics, mockStyle, mockObserver);
        verify(mockChild2).draw(100, 200, 1.5f, mockGraphics, mockStyle, mockObserver);
    }
    
    @Test
    public void testGetAndSetName() {
        // Test getName
        assertEquals("Test Group", compositeItem.getName());
        
        // Test setName
        compositeItem.setName("New Name");
        assertEquals("New Name", compositeItem.getName());
    }
    
    @Test
    public void testToString() {
        // Test toString with empty composite
        String emptyString = compositeItem.toString();
        assertTrue(emptyString.contains("1"));
        assertTrue(emptyString.contains("Test Group"));
        assertTrue(emptyString.contains("0 items"));
        
        // Add children and test again
        compositeItem.add(mockChild1);
        compositeItem.add(mockChild2);
        
        String fullString = compositeItem.toString();
        assertTrue(fullString.contains("1"));
        assertTrue(fullString.contains("Test Group"));
        assertTrue(fullString.contains("2 items"));
    }
}
