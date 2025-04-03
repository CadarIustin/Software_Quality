package jabberpoint.util;

import java.io.IOException;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;

public class DemoLoader implements PresentationLoader {
    @Override
    public void loadPresentation(Presentation presentation, String unusedFilename) throws IOException {
        presentation.setTitle("Demo Presentation");
        
        Slide slide;
        
        slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.append(1, "The Java presentation tool");
        slide.append(2, "Copyright (c) 1996-2025: Ian Darwin, Gert Florijn, Sylvia Stuurman");
        slide.append(4, "Calling the Presentation Loader:");
        slide.append(4, "Presenter starts with empty presentation");
        slide.append(4, "File, Open reads a presentation file");
        slide.append(1, "This is the end of the demo presentation");
        presentation.addSlide(slide);
        
        slide = new Slide();
        slide.setTitle("Demo Slide 2");
        slide.append(1, "Demonstrating a slide with an image");
        slide.append(new BitmapItem(1, "JabberPoint.gif"));
        presentation.addSlide(slide);
        
        slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(1, "To open a new presentation,");
        slide.append(2, "use File->Open from the menu.");
        slide.append(1, "This is the end of the presentation");
        presentation.addSlide(slide);
    }
}
