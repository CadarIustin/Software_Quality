package jabberpoint.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jabberpoint.model.BitmapItem;
import jabberpoint.model.Presentation;
import jabberpoint.model.Slide;
import jabberpoint.model.SlideItem;
import jabberpoint.model.TextItem;

/**
 * XMLAccessor for loading and saving presentations in XML format
 * @author JabberPoint team
 */
public class XMLAccessor implements PresentationLoader {
    protected static final String DEFAULT_API_TO_USE = "dom";
    protected static final String SHOWTITLE = "showtitle";
    protected static final String SLIDETITLE = "title";
    protected static final String SLIDE = "slide";
    protected static final String ITEM = "item";
    protected static final String LEVEL = "level";
    protected static final String KIND = "kind";
    protected static final String TEXT = "text";
    protected static final String IMAGE = "image";
    protected static final String PCE = "Parser Configuration Exception";
    protected static final String UNKNOWNTYPE = "Unknown Element type";
    protected static final String NFE = "Number Format Exception";
    
    // Added constants for saving
    protected static final String PRESENTATION_OPENING_TAG = "<?xml version=\"1.0\"?>\n<!DOCTYPE presentation SYSTEM \"presentation.dtd\">\n<presentation>";
    protected static final String PRESENTATION_CLOSING_TAG = "</presentation>";
    protected static final String INDENTATION = "    ";

    @Override
    public void loadPresentation(Presentation presentation, String filename) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            Element doc = document.getDocumentElement();
            
            NodeList titles = doc.getElementsByTagName(SHOWTITLE);
            String title = titles.item(0).getTextContent();
            presentation.setTitle(title);
            
            NodeList slides = doc.getElementsByTagName(SLIDE);
            for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
                Element xmlSlide = (Element) slides.item(slideNumber);
                Slide slide = new Slide();
                
                NodeList slideTitle = xmlSlide.getElementsByTagName(SLIDETITLE);
                slide.setTitle(slideTitle.item(0).getTextContent());
                
                NodeList items = xmlSlide.getElementsByTagName(ITEM);
                for (int itemNumber = 0; itemNumber < items.getLength(); itemNumber++) {
                    Element item = (Element) items.item(itemNumber);
                    loadSlideItem(slide, item);
                }
                presentation.addSlide(slide);
            }
        } 
        catch (ParserConfigurationException pce) {
            System.err.println(PCE + ": " + pce.getMessage());
        }
        catch (SAXException saxe) {
            System.err.println(saxe.getMessage());
        }
    }
    
    private void loadSlideItem(Slide slide, Element item) {
        int level = 1;
        NamedNodeMap attributes = item.getAttributes();
        String leveltext = attributes.getNamedItem(LEVEL).getTextContent();
        if (leveltext != null) {
            try {
                level = Integer.parseInt(leveltext);
            }
            catch(NumberFormatException nfe) {
                System.err.println(NFE);
            }
        }
        
        String type = attributes.getNamedItem(KIND).getTextContent();
        if (TEXT.equals(type)) {
            slide.append(new TextItem(level, item.getTextContent()));
        }
        else if (IMAGE.equals(type)) {
            slide.append(new BitmapItem(level, "img/" + item.getTextContent()));
        }
        else {
            System.err.println(UNKNOWNTYPE);
        }
    }
    
    /**
     * Saves a presentation to XML format
     * @param presentation the presentation to save
     * @param filename the filename to save to
     * @throws IOException if saving fails
     */
    public void savePresentation(Presentation presentation, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println(PRESENTATION_OPENING_TAG);
            out.println(INDENTATION + "<" + SHOWTITLE + ">" + presentation.getTitle() + "</" + SHOWTITLE + ">");
            
            for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
                Slide slide = presentation.getSlide(slideNumber);
                out.println(INDENTATION + "<" + SLIDE + ">");
                out.println(INDENTATION + INDENTATION + "<" + SLIDETITLE + ">" + slide.getTitle() + "</" + SLIDETITLE + ">");
                
                for (int itemNumber = 0; itemNumber < slide.getSize(); itemNumber++) {
                    SlideItem item = slide.getSlideItem(itemNumber);
                    saveSlideItem(out, item);
                }
                out.println(INDENTATION + "</" + SLIDE + ">");
            }
            
            out.println(PRESENTATION_CLOSING_TAG);
        }
    }
    
    private void saveSlideItem(PrintWriter out, SlideItem item) {
        out.print(INDENTATION + INDENTATION + "<" + ITEM);
        out.print(" " + LEVEL + "=\"" + item.getLevel() + "\"");
        
        if (item instanceof TextItem) {
            out.print(" " + KIND + "=\"" + TEXT + "\"");
            out.println(">" + ((TextItem) item).getText() + "</" + ITEM + ">");
        } else if (item instanceof BitmapItem) {
            out.print(" " + KIND + "=\"" + IMAGE + "\"");
            String imagePath = ((BitmapItem) item).getName();
            // Remove the img/ prefix if it exists for storage
            if (imagePath.startsWith("img/")) {
                imagePath = imagePath.substring(4);
            }
            out.println(">" + imagePath + "</" + ITEM + ">");
        } else {
            out.println("/>");
        }
    }
}
