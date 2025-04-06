package jabberpoint.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

/**
 * Unit test for the SlideEditorPanel class
 */
public class SlideEditorPanelTest {
    
    private SlideEditorPanel editorPanel;
    private Presentation presentation;
    
    @BeforeEach
    void setUp() {
        // Create a real presentation
        presentation = new Presentation();
        presentation.setTitle("Test Presentation");
        
        // Add a test slide
        Slide slide = new Slide();
        slide.setTitle("Test Slide");
        slide.append(new TextItem(1, "Test Item"));
        presentation.addSlide(slide);
        presentation.setSlideNumber(0);
        
        // Create the editor panel
        editorPanel = new SlideEditorPanel(presentation);
    }
    
    @Test
    void testInitialization() {
        // Verify the panel was created
        assertNotNull(editorPanel);
        
        // Get the title field using reflection
        JTextField titleField = getPrivateField("titleField", JTextField.class);
        assertNotNull(titleField);
        
        // Verify the title field was initialized with the current slide title
        assertEquals("Test Slide", titleField.getText());
    }
    
    @Test
    void testUpdateSlideTitle() throws Exception {
        // Get the title field using reflection
        JTextField titleField = getPrivateField("titleField", JTextField.class);
        assertNotNull(titleField);
        
        // Set a new title
        titleField.setText("Updated Slide Title");
        
        // Call the updateCurrentSlideTitle method
        Method updateTitleMethod = SlideEditorPanel.class.getDeclaredMethod("updateCurrentSlideTitle");
        updateTitleMethod.setAccessible(true);
        updateTitleMethod.invoke(editorPanel);
        
        // Verify the slide title was updated
        assertEquals("Updated Slide Title", presentation.getCurrentSlide().getTitle());
    }
    
    @Test
    void testAddTextItem() throws Exception {
        // Get the necessary fields using reflection
        @SuppressWarnings("unchecked")
        JComboBox<String> itemTypeCombo = getPrivateField("itemTypeCombo", JComboBox.class);
        JTextField itemContentField = getPrivateField("itemContentField", JTextField.class);
        @SuppressWarnings("unchecked")
        JComboBox<Integer> levelCombo = getPrivateField("levelCombo", JComboBox.class);
        
        // Set up the fields for adding a text item
        itemTypeCombo.setSelectedItem("Text");
        itemContentField.setText("New Text Item");
        levelCombo.setSelectedItem(2);
        
        // Call the addItemToCurrentSlide method
        Method addItemMethod = SlideEditorPanel.class.getDeclaredMethod("addItemToCurrentSlide");
        addItemMethod.setAccessible(true);
        addItemMethod.invoke(editorPanel);
        
        // Verify the item was added to the slide
        Slide currentSlide = presentation.getCurrentSlide();
        assertEquals(2, currentSlide.getSize());
        SlideItem addedItem = currentSlide.getSlideItem(1);
        assertTrue(addedItem instanceof TextItem);
        assertEquals(2, addedItem.getLevel());
        assertEquals("New Text Item", ((TextItem) addedItem).getText());
    }
    
    @Test
    void testAddImageItem() throws Exception {
        // Get the necessary fields using reflection
        @SuppressWarnings("unchecked")
        JComboBox<String> itemTypeCombo = getPrivateField("itemTypeCombo", JComboBox.class);
        JTextField itemContentField = getPrivateField("itemContentField", JTextField.class);
        @SuppressWarnings("unchecked")
        JComboBox<Integer> levelCombo = getPrivateField("levelCombo", JComboBox.class);
        
        // Set up the fields for adding an image item
        itemTypeCombo.setSelectedItem("Image");
        itemContentField.setText("img/test.jpg");
        levelCombo.setSelectedItem(1);
        
        // Call the addItemToCurrentSlide method
        Method addItemMethod = SlideEditorPanel.class.getDeclaredMethod("addItemToCurrentSlide");
        addItemMethod.setAccessible(true);
        addItemMethod.invoke(editorPanel);
        
        // Verify the item was added to the slide
        Slide currentSlide = presentation.getCurrentSlide();
        assertEquals(2, currentSlide.getSize());
        SlideItem addedItem = currentSlide.getSlideItem(1);
        assertTrue(addedItem instanceof BitmapItem);
        assertEquals(1, addedItem.getLevel());
        assertEquals("img/test.jpg", ((BitmapItem) addedItem).getName());
    }
    
    @Test
    void testCreateNewSlide() throws Exception {
        // Get the initial number of slides
        int initialSlideCount = presentation.getSize();
        
        // Call the createNewSlide method
        Method createSlideMethod = SlideEditorPanel.class.getDeclaredMethod("createNewSlide");
        createSlideMethod.setAccessible(true);
        createSlideMethod.invoke(editorPanel);
        
        // Verify a new slide was added
        assertEquals(initialSlideCount + 1, presentation.getSize());
        assertEquals(initialSlideCount, presentation.getSlideNumber()); // Should be the last slide
        assertEquals("New Slide", presentation.getCurrentSlide().getTitle());
    }
    
    @Test
    void testAddItemToNonExistentSlide() throws Exception {
        // Remove all slides
        presentation.clear();
        assertEquals(0, presentation.getSize());
        
        // Get the necessary fields using reflection
        @SuppressWarnings("unchecked")
        JComboBox<String> itemTypeCombo = getPrivateField("itemTypeCombo", JComboBox.class);
        JTextField itemContentField = getPrivateField("itemContentField", JTextField.class);
        @SuppressWarnings("unchecked")
        JComboBox<Integer> levelCombo = getPrivateField("levelCombo", JComboBox.class);
        
        // Set up the fields for adding a text item
        itemTypeCombo.setSelectedItem("Text");
        itemContentField.setText("New Text Item");
        levelCombo.setSelectedItem(1);
        
        // Call the addItemToCurrentSlide method
        Method addItemMethod = SlideEditorPanel.class.getDeclaredMethod("addItemToCurrentSlide");
        addItemMethod.setAccessible(true);
        addItemMethod.invoke(editorPanel);
        
        // Verify a new slide was created and the item was added
        assertEquals(1, presentation.getSize());
        Slide newSlide = presentation.getCurrentSlide();
        assertNotNull(newSlide);
        assertEquals(1, newSlide.getSize());
        assertTrue(newSlide.getSlideItem(0) instanceof TextItem);
    }
    
    /**
     * Helper method to get a private field using reflection
     */
    @SuppressWarnings("unchecked")
    private <T> T getPrivateField(String fieldName, Class<T> fieldType) {
        try {
            Field field = SlideEditorPanel.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(editorPanel);
        } catch (Exception e) {
            fail("Failed to access field " + fieldName + ": " + e.getMessage());
            return null;
        }
    }
}
