package jabberpoint;

import java.awt.Color;
import java.awt.Font;

/** <p>Style is for Indent, Color, Font and Leading.</p>
 * <p>Direct relation between style-number and item-level:
 * in Slide style if fetched for an item
 * with style-number as item-level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2025/04/03 Iustin Cadar (Modified to use ThemeStrategy for Strategy pattern)
 */

public class Style {
	private static Style[] styles; // de styles
	private static ThemeStrategy currentTheme = new DefaultTheme(); // Default theme
	
	int indent;
	Color color;
	Font font;
	int fontSize;
	int leading;

	public static void createStyles() {
		styles = new Style[5];    
		// Create styles based on the current theme
		createStylesForTheme(currentTheme);
	}
	
	/**
	 * Set the theme to use for the presentation
	 * @param theme the theme to use
	 */
	public static void setTheme(ThemeStrategy theme) {
	    if (theme != null) {
	        currentTheme = theme;
	        // Update styles using the new theme
	        if (styles != null) {
	            createStylesForTheme(theme);
	        }
	    }
	}
	
	/**
	 * Get the current theme being used
	 * @return the current theme
	 */
	public static ThemeStrategy getCurrentTheme() {
	    return currentTheme;
	}
	
	/**
	 * Create styles based on a specific theme
	 * @param theme the theme to use for creating styles
	 */
	private static void createStylesForTheme(ThemeStrategy theme) {
	    // Set indentation levels for different style levels
	    int[] indents = {0, 20, 50, 70, 90};
	    int[] leadings = {20, 10, 10, 10, 10};
	    
	    for (int i = 0; i < styles.length; i++) {
	        Color textColor = i == 0 ? theme.getTitleColor() : theme.getTextColor();
	        styles[i] = new Style(indents[i], textColor, theme.getFontSize(i), leadings[i]);
	    }
	}

	public static Style getStyle(int level) {
		if (level >= styles.length) {
			level = styles.length - 1;
		}
		return styles[level];
	}

	public Style(int indent, Color color, int points, int leading) {
		this.indent = indent;
		this.color = color;
		font = new Font(currentTheme.getThemeName().contains("Dark") ? "Helvetica" : "Arial", Font.BOLD, fontSize=points);
		this.leading = leading;
	}

	public String toString() {
		return "[Style:indent=" + indent + ",color=" + color + ",font=" + font + ",fontSize=" + fontSize + ",leading=" + leading + "]";
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

	public int getIndent() {
		return indent;
	}

	public Color getColor() {
		return color;
	}

	public int getLeading() {
		return leading;
	}
}
