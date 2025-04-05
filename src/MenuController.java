import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import jabberpoint.model.Presentation;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar implements ActionListener {
	
	private Frame parent; // the frame, only used as parent for the Dialogs
	private Presentation presentation; // Commands are given to the presentation
	
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	
	protected static final String TESTFILE = "test.xml";
	protected static final String SAVEFILE = "dump.xml";
	
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
	protected static final String SAVEERR = "Save Error";

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
		fileMenu.addSeparator();
		fileMenu.add(menuItem = mkMenuItem(EXIT));
		menuItem.addActionListener(this);
		add(fileMenu);
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
		setHelpMenu(helpMenu);		// needed for portability (Motif, etc.).
	}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        
        switch(command) {
            case OPEN:
                presentation.clear();
                Accessor xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.loadFile(presentation, TESTFILE);
                    presentation.setSlideNumber(0);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc, 
                    LOADERR, JOptionPane.ERROR_MESSAGE);
                }
                parent.repaint();
                break;
            case NEW:
                presentation.clear();
                parent.repaint();
                break;
            case SAVE:
                xmlAccessor = new XMLAccessor();
                try {
                    xmlAccessor.saveFile(presentation, SAVEFILE);
                } catch (IOException exc) {
                    JOptionPane.showMessageDialog(parent, IOEX + exc, 
                            SAVEERR, JOptionPane.ERROR_MESSAGE);
                }
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
                String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
                int pageNumber = Integer.parseInt(pageNumberStr);
                presentation.setSlideNumber(pageNumber - 1);
                break;
            case ABOUT:
                AboutBox.show(parent);
                break;
        }
    }

// create a menu item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
