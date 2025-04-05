package jabberpoint.util;

import java.io.IOException;
import jabberpoint.model.Presentation;

/**
 * Interface for exporting presentations in different formats.
 * This follows the Strategy pattern to allow for different export strategies.
 */
public interface PresentationExporter {
    /**
     * Export a presentation to a file.
     * 
     * @param presentation The presentation to export
     * @param fileName The name of the file to export to
     * @throws IOException If there is an error writing to the file
     */
    void exportPresentation(Presentation presentation, String fileName) throws IOException;
    
    /**
     * Get the file extension for this exporter.
     * 
     * @return The file extension (without the dot)
     */
    String getFileExtension();
    
    /**
     * Get the description of this exporter.
     * 
     * @return A human-readable description of this exporter
     */
    String getDescription();
}
