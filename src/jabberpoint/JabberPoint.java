package jabberpoint;

import javax.swing.JOptionPane;
import java.io.IOException;

import jabberpoint.model.Presentation;
import jabberpoint.model.Style;
import jabberpoint.util.PresentationLoaderContext;
import jabberpoint.util.XMLLoader;
import jabberpoint.view.SlideViewerFrame;

public class JabberPoint {
    private static final String IOERR = "IO Error: ";
    private static final String JABERR = "Jabberpoint Error ";
    private static final String JABVERSION = "Jabberpoint 2.0";
    
    private static final String WELCOME_MESSAGE = 
            "Welcome to JabberPoint!\n\n" +
            "Navigation Controls:\n" +
            "• Use arrow keys (← →) to move between slides\n" +
            "• Use '+' and '-' keys to navigate forward and backward\n" +
            "• Page Up/Down keys also work for navigation\n" +
            "• Press 'Q' to exit the presentation\n\n" +
            "Use the 'Open' option in the File menu to load presentations.\n" +
            "You can choose to open a file or load the demo presentation.";

    public static void main(String[] argv) {
        Style.createStyles();
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
        
        try {
            if (argv.length > 0) {
                // Only load a file if one is specified on the command line
                // Use the Strategy pattern with XMLLoader
                PresentationLoaderContext loaderContext = new PresentationLoaderContext(new XMLLoader());
                loaderContext.loadPresentation(presentation, argv[0]);
                presentation.setSlideNumber(0);
            } else {
                // Show welcome message with navigation information
                JOptionPane.showMessageDialog(frame, 
                        WELCOME_MESSAGE,
                        "Welcome to JabberPoint", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
            
            frame.setVisible(true); // Make the frame visible to the user
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    IOERR + ex, JABERR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
