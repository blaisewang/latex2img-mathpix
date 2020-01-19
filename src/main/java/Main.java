import org.apache.commons.lang3.SystemUtils;

import java.awt.Toolkit;
import java.io.IOException;


/**
 * Main.java
 * Java AWT wrapper to start up the JavaFX application.
 * Wrapper is used for hide app icon in the Dock.
 */
public class Main {

    /**
     * @param args command line arguments.
     */
    public static void main(String[] args) throws IOException {

        if (SystemUtils.IS_OS_MAC_OSX) {
            // set config file path
            CommonUtils.setConfigFilePath();
            // hide icon in the Dock of macOS
            System.setProperty("apple.awt.UIElement", "true");
            Toolkit.getDefaultToolkit();
        }

        // run JavaFX application
        MainAPP.main(args);

    }

}
