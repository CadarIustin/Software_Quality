package jabberpoint.util;

import jabberpoint.model.Presentation;
import java.io.IOException;

/**
 * Strategy interface for loading presentations.
 * This is part of the Strategy design pattern implementation.
 * Different strategies can be implemented for loading from different sources.
 */
public interface PresentationLoader {
    /**
     * Loads a presentation from a source
     * @param presentation The presentation to load into
     * @param source The source identifier (typically a filename but could be something else)
     * @throws IOException If loading fails
     */
    void loadPresentation(Presentation presentation, String source) throws IOException;
}
