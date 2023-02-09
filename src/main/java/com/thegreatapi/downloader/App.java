package com.thegreatapi.downloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(App.class.getClassLoader().getResourceAsStream("files.properties"));

        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            URL url = new URL(property.getValue().toString());
            String fileName = property.getKey().toString();
            Path path = Paths.get("data/");
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("data/" + fileName)) {
                logger.log(Level.INFO, "creating {0}", fileName);
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
        }
    }
}
