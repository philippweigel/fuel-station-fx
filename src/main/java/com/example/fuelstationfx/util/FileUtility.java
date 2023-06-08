package com.example.fuelstationfx.util;

import java.io.File;
import java.net.URI;

public class FileUtility {

    public static File openLocalFile(String filePath) throws Exception {
        filePath = filePath.replace("\\", "/").replace("file://", "file:///");
        URI uri = new URI(filePath);
        File file = new File(uri);
        if (file.exists()) {
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(file);
            }
            return file;
        } else {
            throw new RuntimeException("File does not exist: " + filePath);
        }
    }
}
