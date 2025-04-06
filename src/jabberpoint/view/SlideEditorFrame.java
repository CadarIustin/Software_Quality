package jabberpoint.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import jabberpoint.model.Presentation;

public class SlideEditorFrame extends JFrame {
    private static final long serialVersionUID = 3227L;
    
    private static final String EDITOR_TITLE = "JabberPoint - Slide Editor";
    private static final int PREFERRED_WIDTH = 600;
    private static final int PREFERRED_HEIGHT = 400;
    
    private SlideEditorPanel editorPanel;
    private Frame parentFrame;

    public SlideEditorFrame(Presentation presentation) {
        super(EDITOR_TITLE);
        
        this.editorPanel = new SlideEditorPanel(presentation);
        this.parentFrame = null;
        
        setupWindow();
    }
    
    public SlideEditorFrame(Frame parent, Presentation presentation) {
        super(EDITOR_TITLE);
        
        this.editorPanel = new SlideEditorPanel(presentation);
        this.parentFrame = parent;
        
        setupWindow();
    }
    
    private void setupWindow() {
        getContentPane().add(editorPanel, BorderLayout.CENTER);
        setSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        
        // Position relative to parent if available
        if (parentFrame != null) {
            setLocationRelativeTo(parentFrame);
        }
        
        setVisible(true);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
