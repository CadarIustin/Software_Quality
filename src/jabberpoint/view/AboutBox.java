package jabberpoint.view;

import java.awt.Frame;
import javax.swing.JOptionPane;

public class AboutBox {
    public static void show(Frame parent) {
        JOptionPane.showMessageDialog(parent,
            "JabberPoint 2.0 - A Presentation Tool\n" +
            "Restructured to implement design patterns:\n" +
            "- Observer Pattern: For view-model communication\n" +
            "- Composite Pattern: For complex slide items\n" +
            "- Strategy Pattern: For loading/saving presentations\n" +
            "Original version by Ian F. Darwin and Gert Florijn\n" +
            "Enhancements by Sylvia Stuurman\n" +
            "UU Version by Sylvia Stuurman\n" +
            "JabberPoint 2.0 version by Cadarustin",
            "About JabberPoint",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
