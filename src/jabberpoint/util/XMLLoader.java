package jabberpoint.util;

import java.io.File;
import java.io.IOException;
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
import jabberpoint.model.TextItem;

public class XMLLoader implements PresentationLoader {
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
            slide.append(new BitmapItem(level, item.getTextContent()));
        }
        else {
            System.err.println(UNKNOWNTYPE);
        }
    }
}
