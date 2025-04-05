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
import java.awt.event.KeyEvent;

import jabberpoint.model.Presentation;
import jabberpoint.model.Style;
import jabberpoint.util.DefaultTheme;
import jabberpoint.util.DarkTheme;
import jabberpoint.util.HTMLExporter;
import jabberpoint.util.PresentationExporter;
import jabberpoint.util.TextExporter;
import jabberpoint.util.ThemeStrategy;
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
    private static final String THEMES = "Themes";
    private static final String DEFAULT_THEME = "Default Theme";
    private static final String DARK_THEME = "Dark Theme";
    private static final String CYCLE_TRANSITION = "Cycle Transition";
    
    private static final String SAVEFILE = "presentation.xml";
    
    private static final String IOEX = "IO Exception: ";
    private static final String LOADERR = "Load Error";
    private static final String SAVEERR = "Save Error";
    private static final String EXPORTERR = "Export Error";

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
        
        Menu viewMenu = new Menu(VIEW);
        viewMenu.add(menuItem = mkMenuItem(NEXT));
        menuItem.addActionListener(this);
        
        viewMenu.add(menuItem = mkMenuItem(PREV));
        menuItem.addActionListener(this);
        
        viewMenu.add(menuItem = mkMenuItem(GOTO));
        menuItem.addActionListener(this);
        
        viewMenu.add(menuItem = new MenuItem(CYCLE_TRANSITION));
        menuItem.addActionListener(this);
        
        add(viewMenu);
        
        // Add Themes menu
        Menu themesMenu = new Menu(THEMES);
        
        themesMenu.add(menuItem = new MenuItem(DEFAULT_THEME));
        menuItem.addActionListener(this);
        
        themesMenu.add(menuItem = new MenuItem(DARK_THEME));
        menuItem.addActionListener(this);
        
        add(themesMenu);
        
        Menu helpMenu = new Menu(HELP);
        helpMenu.add(menuItem = mkMenuItem(ABOUT));
        menuItem.addActionListener(this);
        
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
            case CYCLE_TRANSITION:
                cycleTransition();
                break;
            case DEFAULT_THEME:
                ThemeStrategy theme = new DefaultTheme();
                theme.applyTheme();
                presentation.notifyObservers();
                break;
            case DARK_THEME:
                theme = new DarkTheme();
                theme.applyTheme();
                presentation.notifyObservers();
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
    }
    
    private void savePresentation() {
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
    
    private void exportPresentation(PresentationExporter exporter) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new java.io.File("."));
        fileChooser.setDialogTitle("Export as " + exporter.getDescription());
        int returnVal = fileChooser.showSaveDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                exporter.exportPresentation(presentation, fileChooser.getSelectedFile().getPath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, IOEX + ex, EXPORTERR, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void newPresentation() {
        presentation.clear();
        presentation.setTitle("New Presentation");
        presentation.setSlideNumber(-1);
    }
    
    private void gotoSlide() {
        String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
        if (pageNumberStr != null && !pageNumberStr.isEmpty()) {
            try {
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
            } catch (NumberFormatException ex) {
                // Invalid number format, do nothing
            }
        }
    }
    
    private void openSlideEditor() {
        new SlideEditorFrame(presentation);
    }
    
    private void cycleTransition() {
        // Find the SlideViewerComponent to cycle its transition
        if (parent instanceof jabberpoint.view.SlideViewerFrame) {
            jabberpoint.view.SlideViewerFrame frame = (jabberpoint.view.SlideViewerFrame) parent;
            frame.getSlideViewerComponent().cycleTransitionType();
            String transitionName = frame.getSlideViewerComponent().getCurrentTransitionName();
            JOptionPane.showMessageDialog(parent, 
                "Transition changed to: " + transitionName, 
                "Transition Type", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
