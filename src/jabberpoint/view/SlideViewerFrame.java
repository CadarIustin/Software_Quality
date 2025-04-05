package jabberpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;
import jabberpoint.model.Presentation;
import jabberpoint.util.PresentationTimer;

public class SlideViewerFrame extends JFrame {
    private static final long serialVersionUID = 3227L;
    
    private static final String JABTITLE = "Jabberpoint 2.0";
    private static final int PREFERRED_WIDTH = 1200;
    private static final int PREFERRED_HEIGHT = 800;
    
    private SlideViewerComponent slideViewComponent;
    private PresentationTimer presentationTimer;

    public SlideViewerFrame(Presentation presentation) {
        super(JABTITLE);
        
        slideViewComponent = new SlideViewerComponent(presentation, this);
        presentationTimer = new PresentationTimer();
        presentation.setSlideNumber(0);
        
        setupWindow();
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
    }
    
    public SlideViewerFrame(String title, Presentation presentation) {
        super(title);
        
        slideViewComponent = new SlideViewerComponent(presentation, this);
        presentationTimer = new PresentationTimer();
        presentation.setSlideNumber(0);
        
        setupWindow();
        addKeyListener(new KeyController(presentation));
        setMenuBar(new MenuController(this, presentation));
    }
    
    private void setupWindow() {
        // Create a timer control panel at the bottom
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.add(presentationTimer);
        
        JButton startTimerButton = new JButton("Start");
        startTimerButton.addActionListener(e -> {
            if (presentationTimer.isRunning()) {
                presentationTimer.stop();
                startTimerButton.setText("Start");
            } else {
                presentationTimer.start();
                startTimerButton.setText("Pause");
            }
        });
        timerPanel.add(startTimerButton);
        
        JButton resetTimerButton = new JButton("Reset");
        resetTimerButton.addActionListener(e -> {
            presentationTimer.reset();
            startTimerButton.setText("Start");
        });
        timerPanel.add(resetTimerButton);
        
        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(slideViewComponent, BorderLayout.CENTER);
        getContentPane().add(timerPanel, BorderLayout.SOUTH);
        
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
    
    public PresentationTimer getPresentationTimer() {
        return presentationTimer;
    }
}
