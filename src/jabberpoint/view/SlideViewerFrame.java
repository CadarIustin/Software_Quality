package jabberpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;
import jabberpoint.model.Presentation;

public class SlideViewerFrame extends JFrame {
    private static final long serialVersionUID = 3227L;
    
    private static final String JABTITLE = "Jabberpoint 2.0";
    private static final int PREFERRED_WIDTH = 1200;
    private static final int PREFERRED_HEIGHT = 800;
    
    private SlideViewerComponent slideViewComponent;

    public SlideViewerFrame(Presentation presentation) {
        super(JABTITLE);
        
        slideViewComponent = new SlideViewerComponent(presentation);
        presentation.setSlideNumber(0);
        
        setupWindow();
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
    }
    
    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        
        slideViewComponent = new SlideViewerComponent(presentation);
        presentation.setSlideNumber(0);
        
        setupWindow();
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
    }
    
    private void setupWindow() {
        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(slideViewComponent, BorderLayout.CENTER);
        
        setSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    public SlideViewerComponent getSlideViewerComponent() {
        return slideViewComponent;
    }
}
