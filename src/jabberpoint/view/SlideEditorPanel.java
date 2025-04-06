package jabberpoint.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

public class SlideEditorPanel extends JPanel {
    private static final long serialVersionUID = 5274610143343089318L;
    
    private Presentation presentation;
    private JTextField titleField;
    private JComboBox<String> itemTypeCombo;
    private JTextField itemContentField;
    private JComboBox<Integer> levelCombo;
    
    public SlideEditorPanel(Presentation presentation) {
        this.presentation = presentation;
        
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Create slide title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JLabel("Slide Title:"), BorderLayout.WEST);
        titleField = new JTextField(20);
        titlePanel.add(titleField, BorderLayout.CENTER);
        
        // Create slide item editor panel
        JPanel itemPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        itemPanel.add(new JLabel("Item Type:"));
        String[] itemTypes = {"Text", "Image"};
        itemTypeCombo = new JComboBox<>(itemTypes);
        itemPanel.add(itemTypeCombo);
        
        itemPanel.add(new JLabel("Content/Path:"));
        itemContentField = new JTextField(20);
        itemPanel.add(itemContentField);
        
        itemPanel.add(new JLabel("Level:"));
        Integer[] levels = {0, 1, 2, 3, 4};
        levelCombo = new JComboBox<>(levels);
        levelCombo.setSelectedIndex(1); // Default to level 1
        itemPanel.add(levelCombo);
        
        // Create buttons panel
        JPanel buttonsPanel = new JPanel();
        
        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToCurrentSlide();
            }
        });
        buttonsPanel.add(addItemButton);
        
        JButton updateTitleButton = new JButton("Update Title");
        updateTitleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCurrentSlideTitle();
            }
        });
        buttonsPanel.add(updateTitleButton);
        
        JButton newSlideButton = new JButton("New Slide");
        newSlideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewSlide();
            }
        });
        buttonsPanel.add(newSlideButton);
        
        // Add all panels to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(itemPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Initialize with current slide data if available
        updateEditorFromCurrentSlide();
    }
    
    private void updateEditorFromCurrentSlide() {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide != null) {
            titleField.setText(currentSlide.getTitle());
        } else {
            titleField.setText("");
        }
    }
    
    private void updateCurrentSlideTitle() {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide != null) {
            currentSlide.setTitle(titleField.getText());
            presentation.notifyObservers();
        }
    }
    
    private void addItemToCurrentSlide() {
        Slide currentSlide = presentation.getCurrentSlide();
        if (currentSlide == null) {
            // Create a new slide if one doesn't exist
            createNewSlide();
            currentSlide = presentation.getCurrentSlide();
        }
        
        String type = (String) itemTypeCombo.getSelectedItem();
        String content = itemContentField.getText();
        int level = (Integer) levelCombo.getSelectedItem();
        
        switch (type) {
            case "Text":
                currentSlide.append(new TextItem(level, content));
                break;
                
            case "Image":
                currentSlide.append(new BitmapItem(level, content));
                break;
        }
        
        presentation.notifyObservers();
        
        // Reset content field
        itemContentField.setText("");
    }
    
    private void createNewSlide() {
        Slide newSlide = new Slide();
        newSlide.setTitle("New Slide");
        presentation.addSlide(newSlide);
        presentation.setSlideNumber(presentation.getSize() - 1);
        
        // Update editor with new slide
        updateEditorFromCurrentSlide();
    }
}
