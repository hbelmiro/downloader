package com.thegreatapi.downloader;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(App.class.getClassLoader().getResourceAsStream("files.properties"));

        for(Map.Entry<Object, Object> property : properties.entrySet()) {
            URL url = new URL(property.getValue().toString());
            String fileName = property.getKey().toString();
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("data/" + fileName)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
        }
    }
}
