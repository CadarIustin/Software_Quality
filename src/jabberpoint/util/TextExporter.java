package jabberpoint.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

/**
 * Text exporter for JabberPoint presentations.
 * Exports a presentation as a plain text file.
 */
public class TextExporter implements PresentationExporter {
    
    @Override
    public void exportPresentation(Presentation presentation, String fileName) throws IOException {
        if (!fileName.toLowerCase().endsWith("." + getFileExtension())) {
            fileName += "." + getFileExtension();
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            // Write presentation title
            out.println("PRESENTATION: " + presentation.getTitle());
            out.println("=".repeat(60));
            out.println();
            
            // Export each slide
            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                out.println("SLIDE " + (slideNumber + 1) + ": " + slide.getTitle());
                out.println("-".repeat(40));
                
                // Export slide items
                for (int itemNumber = 0; itemNumber < slide.getSize(); itemNumber++) {
                    SlideItem item = slide.getSlideItem(itemNumber);
                    String indent = " ".repeat(item.getLevel() * 4);
                    
                    if (item instanceof TextItem) {
                        out.println(indent + "* " + ((TextItem) item).getText());
                    } else {
                        out.println(indent + "* " + item.toString());
                    }
                }
                
                out.println();
                out.println();
            }
        }
    }
    
    @Override
    public String getFileExtension() {
        return "txt";
    }
    
    @Override
    public String getDescription() {
        return "Text File (*.txt)";
    }
}
