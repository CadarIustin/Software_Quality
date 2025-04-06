package jabberpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

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
        
        // Create navigation instructions panel
        JPanel instructionsPanel = createNavigationInstructionsPanel();
        getContentPane().add(instructionsPanel, BorderLayout.SOUTH);
        
        setSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    private JPanel createNavigationInstructionsPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setBackground(new Color(240, 240, 240));
        
        String instructions = "<html><body style='width: 600px'>" +
                "<b>Navigation:</b> Use arrow keys (←/→) or +/- keys to navigate between slides. " +
                "Page Up/Down also work. Press 'Q' to exit. " +
                "Open the Help menu for more navigation options.</body></html>";
        
        JLabel label = new JLabel(instructions);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(label);
        
        return panel;
    }
    
    public SlideViewerComponent getSlideViewerComponent() {
        return slideViewComponent;
    }
}
