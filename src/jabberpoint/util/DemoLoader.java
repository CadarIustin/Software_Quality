package jabberpoint.util;

import java.io.IOException;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.CompositeSlideItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.TextItem;

public class DemoLoader implements PresentationLoader {
    @Override
    public void loadPresentation(Presentation presentation, String unusedFilename) throws IOException {
        presentation.setTitle("Demo Presentation");
        
        Slide slide;
        
        // Slide 1: Introduction
        slide = new Slide();
        slide.setTitle("JabberPoint");
        slide.append(1, "The Java presentation tool");
        slide.append(2, "Copyright (c) 1996-2025: Ian Darwin, Gert Florijn, Sylvia Stuurman");
        slide.append(4, "Calling the Presentation Loader:");
        slide.append(4, "Presenter starts with empty presentation");
        slide.append(4, "File, Open reads a presentation file");
        slide.append(1, "This is the end of the demo presentation");
        presentation.addSlide(slide);
        
        // Slide 2: Features with image
        slide = new Slide();
        slide.setTitle("Demo Slide 2");
        slide.append(1, "Demonstrating a slide with an image");
        slide.append(new BitmapItem(1, "JabberPoint.gif"));
        presentation.addSlide(slide);
        
        // Slide 3: Composite pattern example
        slide = new Slide();
        slide.setTitle("Composite Pattern Example");
        
        CompositeSlideItem composite = new CompositeSlideItem(1, "Group 1");
        composite.add(new TextItem(2, "This is text in a group"));
        composite.add(new TextItem(2, "Another text item in the same group"));
        
        slide.append(1, "This slide demonstrates the Composite pattern");
        slide.append(composite);
        slide.append(1, "Items can be grouped and treated as a single item");
        
        presentation.addSlide(slide);
        
        // Slide 4: Observer pattern example
        slide = new Slide();
        slide.setTitle("Observer Pattern Demo");
        slide.append(1, "The Observer pattern is used in JabberPoint to:");
        slide.append(2, "- Update the view when the presentation model changes");
        slide.append(2, "- Maintain separation between model and view");
        slide.append(2, "- Allow multiple views of the same presentation");
        slide.append(1, "Try navigating between slides to see the Observer pattern in action");
        
        presentation.addSlide(slide);
    }
}
