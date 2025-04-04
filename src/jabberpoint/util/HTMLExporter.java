package jabberpoint.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;
import jabberpoint.model.BitmapItem;

/**
 * HTML exporter for JabberPoint presentations.
 * Exports a presentation as an HTML file.
 */
public class HTMLExporter implements PresentationExporter {
    
    @Override
    public void exportPresentation(Presentation presentation, String fileName) throws IOException {
        if (!fileName.toLowerCase().endsWith("." + getFileExtension())) {
            fileName += "." + getFileExtension();
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            // Write HTML header
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("  <meta charset=\"UTF-8\">");
            out.println("  <title>" + presentation.getTitle() + "</title>");
            out.println("  <style>");
            out.println("    body { font-family: Arial, sans-serif; margin: 20px; }");
            out.println("    .slide { border: 1px solid #ccc; margin-bottom: 20px; padding: 20px; }");
            out.println("    .slide-title { font-size: 24px; font-weight: bold; margin-bottom: 10px; }");
            out.println("    .slide-item { margin-left: 20px; margin-bottom: 10px; }");
            out.println("    .level1 { font-size: 20px; font-weight: bold; }");
            out.println("    .level2 { font-size: 18px; margin-left: 20px; }");
            out.println("    .level3 { font-size: 16px; margin-left: 40px; }");
            out.println("    .level4 { font-size: 14px; margin-left: 60px; }");
            out.println("    img { max-width: 100%; height: auto; }");
            out.println("  </style>");
            out.println("</head>");
            out.println("<body>");
            out.println("  <h1>" + presentation.getTitle() + "</h1>");
            
            // Export each slide
            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                out.println("  <div class=\"slide\">");
                out.println("    <div class=\"slide-title\">" + slide.getTitle() + "</div>");
                
                // Export slide items
                for (int itemNumber = 0; itemNumber < slide.getSize(); itemNumber++) {
                    SlideItem item = slide.getSlideItem(itemNumber);
                    out.println("    <div class=\"slide-item level" + (item.getLevel() + 1) + "\">");
                    
                    if (item instanceof TextItem) {
                        out.println(((TextItem) item).getText());
                    } else if (item instanceof BitmapItem) {
                        BitmapItem bitmapItem = (BitmapItem) item;
                        out.println("<img src=\"" + bitmapItem.getImageFile() + "\" alt=\"Slide image\">");
                    } else {
                        out.println(item.toString());
                    }
                    
                    out.println("    </div>");
                }
                
                out.println("  </div>");
            }
            
            // Write HTML footer
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    public String getFileExtension() {
        return "html";
    }
    
    @Override
    public String getDescription() {
        return "HTML File (*.html)";
    }
}
