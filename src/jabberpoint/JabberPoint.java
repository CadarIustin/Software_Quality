package jabberpoint;

import javax.swing.JOptionPane;
import java.io.IOException;

import jabberpoint.model.Presentation;
import jabberpoint.model.Style;
import jabberpoint.util.DemoLoader;
import jabberpoint.util.XMLLoader;
import jabberpoint.view.SlideViewerFrame;

public class JabberPoint {
    private static final String IOERR = "IO Error: ";
    private static final String JABERR = "Jabberpoint Error ";
    private static final String JABVERSION = "Jabberpoint 2.0";

    public static void main(String[] argv) {
        Style.createStyles();
        Presentation presentation = new Presentation();
        SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);
        
        try {
            if (argv.length == 0) {
                // Load demo presentation if no file is specified
                DemoLoader demoLoader = new DemoLoader();
                demoLoader.loadPresentation(presentation, "");
            } else {
                // Load the specified XML file
                XMLLoader xmlLoader = new XMLLoader();
                xmlLoader.loadPresentation(presentation, argv[0]);
            }
            presentation.setSlideNumber(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    IOERR + ex, JABERR,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
