package jabberpoint.util;

import jabberpoint.model.Style;

public interface ThemeStrategy {
    void applyTheme();
    String getThemeName();
    Style getStyle(int level);
}
