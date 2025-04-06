package jabberpoint.util;

import java.io.IOException;
import jabberpoint.model.Presentation;

/**
 * Context class for the Strategy pattern for loading presentations.
 * This class maintains a reference to a PresentationLoader strategy
 * and delegates to it for loading presentations.
 */
public class PresentationLoaderContext {
    private PresentationLoader loaderStrategy;
    
    /**
     * Create a context with the given loader strategy
     * @param loaderStrategy The strategy to use for loading presentations
     */
    public PresentationLoaderContext(PresentationLoader loaderStrategy) {
        this.loaderStrategy = loaderStrategy;
    }
    
    /**
     * Change the loader strategy at runtime
     * @param loaderStrategy The new strategy to use
     */
    public void setLoaderStrategy(PresentationLoader loaderStrategy) {
        this.loaderStrategy = loaderStrategy;
    }
    
    /**
     * Load a presentation using the current strategy
     * @param presentation The presentation to load into
     * @param source The source identifier
     * @throws IOException If loading fails
     */
    public void loadPresentation(Presentation presentation, String source) throws IOException {
        if (loaderStrategy == null) {
            throw new IllegalStateException("No loader strategy set");
        }
        loaderStrategy.loadPresentation(presentation, source);
    }
    
    /**
     * Get the current loader strategy
     * @return The current PresentationLoader strategy
     */
    public PresentationLoader getLoaderStrategy() {
        return loaderStrategy;
    }
}
