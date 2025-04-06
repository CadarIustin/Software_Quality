package jabberpoint.controller;

import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jabberpoint.model.Presentation;
import jabberpoint.util.DemoLoader;
import jabberpoint.util.PresentationLoaderContext;
import jabberpoint.util.XMLLoader;
import jabberpoint.view.AboutBox;
import jabberpoint.view.SlideEditorFrame;

public class MenuController extends MenuBar implements ActionListener {
    private static final long serialVersionUID = 227L;
    
    private Frame parent;
    private Presentation presentation;
    private PresentationLoaderContext loaderContext; // Added for Strategy pattern
    
    private static final String ABOUT = "About";
    private static final String FILE = "File";
    private static final String EXIT = "Exit";
    private static final String GOTO = "Go to";
    private static final String HELP = "Help";
    private static final String NEW = "New";
    private static final String NEXT = "Next";
    private static final String OPEN = "Open";
    private static final String PAGENR = "Page number?";
    private static final String PREV = "Prev";
    private static final String EDIT = "Edit";
    private static final String EDIT_PRESENTATION = "Edit Presentation";
    private static final String VIEW = "View";
    
    protected static final String DEFAULT_SAVEFILE = "presentation.xml";
    
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private static final String NAVIGATION_HELP_TEXT = 
            "Navigation Controls:\n\n" +
            "Next Slide:\n" +
            "- Right Arrow Key\n" +
            "- Down Arrow Key\n" +
            "- Page Down Key\n" +
            "- Enter Key\n" +
            "- '+' Key\n\n" +
            "Previous Slide:\n" +
            "- Left Arrow Key\n" +
            "- Up Arrow Key\n" +
            "- Page Up Key\n" +
            "- '-' Key\n\n" +
            "Exit Presentation:\n" +
            "- 'Q' Key\n\n" +
            "You can also use the View menu to navigate between slides.";
    
    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        
        // Initialize with default XMLLoader strategy
        loaderContext = new PresentationLoaderContext(new XMLLoader());
        
        MenuItem menuItem;
        
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(this);
        
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(this);
        
        fileMenu.addSeparator();
        
        fileMenu.add(menuItem = mkMenuItem(EXIT));
        menuItem.addActionListener(this);
        
        add(fileMenu);
        
        // Edit menu
        Menu editMenu = new Menu(EDIT);
        editMenu.add(menuItem = mkMenuItem(EDIT_PRESENTATION));
        menuItem.addActionListener(this);
        
        add(editMenu);
        
        // View menu with navigation hint
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(this);
        
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(this);
        
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(this);
        
        add(viewMenu);
        
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(this);
        
        helpMenu.add(menuItem = new MenuItem("Navigation Help"));
        menuItem.addActionListener(e -> showNavigationHelp());
        
        setHelpMenu(helpMenu);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        
        switch (command) {
            case OPEN:
                openPresentation();
                break;
            case NEW:
                newPresentation();
                break;
            case EXIT:
                System.exit(0);
                break;
            case NEXT:
                presentation.nextSlide();
                break;
            case PREV:
                presentation.previousSlide();
                break;
            case GOTO:
                gotoSlide();
                break;
            case ABOUT:
                AboutBox.show(parent);
                break;
            case EDIT_PRESENTATION:
                openSlideEditor();
                break;
            default:
                // Unknown command - do nothing
                break;
        }
    }

    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
    
    private void openPresentation() {
        Object[] options = {"Open File", "Load Demo", "Cancel"};
        int choice = JOptionPane.showOptionDialog(parent,
                "Choose a presentation source:",
                "Open Presentation",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        
        if (choice == 0) {
            // Open File option selected - use XMLLoader strategy
            loaderContext.setLoaderStrategy(new XMLLoader());
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int returnVal = fileChooser.showOpenDialog(parent);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    presentation.clear();
                    loaderContext.loadPresentation(presentation, fileChooser.getSelectedFile().getPath());
                    presentation.setSlideNumber(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parent, IOEX + ex,
                            LOADERR, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (choice == 1) {
            // Load Demo option selected - use DemoLoader strategy
            loadDemoPresentation();
        }
        // If Cancel was selected, do nothing
    }
    
    private void newPresentation() {
        presentation.clear();
        parent.repaint();
    }
    
    private void gotoSlide() {
        String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
        int pageNumber = 0;
        
        try {
            pageNumber = Integer.parseInt(pageNumberStr);
            if (pageNumber > 0 && pageNumber <= presentation.getSize()) {
                presentation.setSlideNumber(pageNumber - 1);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parent, "Invalid slide number: " + pageNumberStr);
        }
    }
    
    private void openSlideEditor() {
        SlideEditorFrame editor = new SlideEditorFrame(presentation);
        editor.setVisible(true);
    }
    
    private void loadDemoPresentation() {
        // Switch to DemoLoader strategy
        loaderContext.setLoaderStrategy(new DemoLoader());
        
        presentation.clear();
        try {
            loaderContext.loadPresentation(presentation, "");
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, IOEX + ex,
                    LOADERR, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showNavigationHelp() {
        JOptionPane.showMessageDialog(parent, NAVIGATION_HELP_TEXT, 
                "Navigation Controls", JOptionPane.INFORMATION_MESSAGE);
    }
}
