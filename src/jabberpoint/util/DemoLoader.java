package jabberpoint.util;

import java.io.IOException;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.CompositeSlideItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.ShapeItem;
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
        composite.add(new ShapeItem(2, ShapeItem.ShapeType.RECTANGLE, 100, 50));
        
        slide.append(1, "This slide demonstrates the Composite pattern");
        slide.append(composite);
        slide.append(1, "Items can be grouped and treated as a single item");
        
        presentation.addSlide(slide);
        
        // Slide 4: Shape examples
        slide = new Slide();
        slide.setTitle("Shape Items Demo");
        slide.append(1, "Different types of shapes:");
        slide.append(new ShapeItem(1, ShapeItem.ShapeType.RECTANGLE, 150, 100));
        slide.append(new ShapeItem(1, ShapeItem.ShapeType.CIRCLE, 120, 120));
        slide.append(new ShapeItem(1, ShapeItem.ShapeType.LINE, 200, 80));
        slide.append(new ShapeItem(1, ShapeItem.ShapeType.TRIANGLE, 150, 120));
        
        presentation.addSlide(slide);
        
        // Slide 5: Final slide
        slide = new Slide();
        slide.setTitle("The Final Slide");
        slide.append(1, "To open a new presentation,");
        slide.append(2, "use File->Open from the menu.");
        slide.append(1, "To change the theme, use the Themes menu.");
        slide.append(1, "This is the end of the presentation");
        presentation.addSlide(slide);
    }
}
