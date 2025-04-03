package jabberpoint.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

public class XMLSaver implements PresentationSaver {
    private static final String DEFAULT_EXTENSION = ".xml";
    private static final String XML_HEADER = "<?xml version=\"1.0\"?>\n<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">\n";
    private static final String PRESENTATION_START = "<presentation>\n";
    private static final String PRESENTATION_END = "</presentation>";
    private static final String TITLE = "<showtitle>%s</showtitle>\n";
    private static final String SLIDE = "<slide>\n";
    private static final String SLIDE_END = "</slide>\n";
    private static final String SLIDE_TITLE = "<title>%s</title>\n";
    private static final String ITEM = "<item kind=\"%s\" level=\"%d\">%s</item>\n";

    @Override
    public void savePresentation(Presentation presentation, String filename) throws IOException {
        if (!filename.endsWith(DEFAULT_EXTENSION)) {
            filename += DEFAULT_EXTENSION;
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.print(XML_HEADER);
            out.print(PRESENTATION_START);
            out.printf(TITLE, presentation.getTitle());
            
            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                out.print(SLIDE);
                out.printf(SLIDE_TITLE, slide.getTitle());
                saveSlideItems(out, slide.getSlideItems());
                out.print(SLIDE_END);
            }
            
            out.print(PRESENTATION_END);
        }
    }
    
    private void saveSlideItems(PrintWriter out, List<SlideItem> items) {
        for (SlideItem item : items) {
            if (item instanceof TextItem) {
                TextItem textItem = (TextItem) item;
                out.printf(ITEM, "text", item.getLevel(), textItem.getText());
            } else if (item instanceof BitmapItem) {
                BitmapItem bitmapItem = (BitmapItem) item;
                out.printf(ITEM, "image", item.getLevel(), bitmapItem.getName());
            }
        }
    }
}
