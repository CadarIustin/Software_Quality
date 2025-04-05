import jabberpoint.model.Presentation;

/**
 * <p>Redirector for the jabberpoint.view.SlideViewerFrame class</p>
 * <p>This class simply extends the proper implementation in the jabberpoint.view package</p>
 */
public class SlideViewerFrame extends jabberpoint.view.SlideViewerFrame {
    private static final long serialVersionUID = 3228L;
    
    public SlideViewerFrame(String title, Presentation presentation) {
        super(title, presentation);
    }
    
    public SlideViewerFrame(Presentation presentation) {
        super(presentation);
    }
}
