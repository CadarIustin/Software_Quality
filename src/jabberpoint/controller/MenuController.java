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
import jabberpoint.util.HTMLExporter;
import jabberpoint.util.PresentationExporter;
import jabberpoint.util.TextExporter;
import jabberpoint.util.XMLLoader;
import jabberpoint.util.XMLSaver;
import jabberpoint.view.AboutBox;
import jabberpoint.view.SlideEditorFrame;

public class MenuController extends MenuBar implements ActionListener {
    private static final long serialVersionUID = 227L;
    
    private Frame parent;
    private Presentation presentation;
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
    private static final String SAVE = "Save";
    private static final String EXPORT = "Export";
    private static final String EXPORT_HTML = "Export to HTML";
    private static final String EXPORT_TEXT = "Export to Text";
    private static final String EDIT = "Edit";
    private static final String EDIT_PRESENTATION = "Edit Presentation";
    private static final String VIEW = "View";
    
    protected static final String DEFAULT_SAVEFILE = "presentation.xml";
    
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private static final String SAVEERR = "Save Error";
    private static final String EXPORTERR = "Export Error";
    
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
        
        MenuItem menuItem;
        
        Menu fileMenu = new Menu(FILE);
        fileMenu.add(menuItem = mkMenuItem(OPEN));
        menuItem.addActionListener(this);
        
        fileMenu.add(menuItem = mkMenuItem(NEW));
        menuItem.addActionListener(this);
        
        fileMenu.add(menuItem = mkMenuItem(SAVE));
        menuItem.addActionListener(this);
        
        // Export submenu
        Menu exportMenu = new Menu(EXPORT);
        
        exportMenu.add(menuItem = new MenuItem(EXPORT_HTML));
        menuItem.addActionListener(this);
        
        exportMenu.add(menuItem = new MenuItem(EXPORT_TEXT));
        menuItem.addActionListener(this);
        
        fileMenu.add(exportMenu);
        
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
    
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        
        switch(command) {
            case OPEN:
                openPresentation();
                break;
            case NEW:
                newPresentation();
                break;
            case SAVE:
                savePresentation();
                break;
            case EXIT:
                presentation.exit(0);
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
            case EXPORT_HTML:
                exportPresentation(new HTMLExporter());
                break;
            case EXPORT_TEXT:
                exportPresentation(new TextExporter());
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
            // Open File option selected
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new java.io.File("."));
            int returnVal = fileChooser.showOpenDialog(parent);
            
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    XMLLoader loader = new XMLLoader();
                    presentation.clear();
                    loader.loadPresentation(presentation, fileChooser.getSelectedFile().getPath());
                    presentation.setSlideNumber(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parent, IOEX + ex, LOADERR, JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (choice == 1) {
            // Load Demo option selected
            loadDemoPresentation();
        }
        // If Cancel (choice == 2) or dialog closed, do nothing
    }
    
    private void savePresentation() {
        if (presentation.getTitle() == null || presentation.getTitle().isEmpty()) {
            presentation.setTitle("Saved Presentation");
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        int returnVal = fileChooser.showSaveDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                XMLSaver saver = new XMLSaver();
                saver.savePresentation(presentation, fileChooser.getSelectedFile().getPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, IOEX + ex, SAVEERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void newPresentation() {
        presentation.clear();
        presentation.setTitle("New Presentation");
        presentation.setSlideNumber(-1);
    }
    
    private void exportPresentation(PresentationExporter exporter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        int returnVal = fileChooser.showSaveDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                exporter.exportPresentation(presentation, fileChooser.getSelectedFile().getPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, IOEX + ex, EXPORTERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void gotoSlide() {
        String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1);
        } catch (NumberFormatException nfe) {
            // Do nothing if invalid number format
        } catch (IllegalArgumentException iae) {
            // Do nothing if slide number out of range
        }
    }
    
    private void openSlideEditor() {
        new SlideEditorFrame(parent, presentation);
    }
    
    private void loadDemoPresentation() {
        try {
            DemoLoader demoLoader = new DemoLoader();
            presentation.clear();
            demoLoader.loadPresentation(presentation, "");
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, IOEX + ex, LOADERR, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showNavigationHelp() {
        JOptionPane.showMessageDialog(
                parent,
                NAVIGATION_HELP_TEXT,
                "Navigation Help",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
