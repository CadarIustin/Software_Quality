package jabberpoint.util;

import jabberpoint.model.Presentation;
import java.io.IOException;

/**
 * Strategy interface for saving presentations.
 * This is part of the Strategy design pattern implementation.
 * Different strategies can be implemented for saving to different formats.
 */
public interface PresentationSaver {
    /**
     * Saves a presentation to a destination
     * @param presentation The presentation to save
     * @param destination The destination identifier (typically a filename)
     * @throws IOException If saving fails
     */
    void savePresentation(Presentation presentation, String destination) throws IOException;
}
