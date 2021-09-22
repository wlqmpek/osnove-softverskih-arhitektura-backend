package ftn.project.models;

import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    private String data;
    private String mime_type;
    private String name;


    public Picture(String sufix) throws Exception {
        String prefix = "Front/osnove-softverskih-arhitektura-front/public";
        Path filePath = new File(prefix + sufix).toPath();
        File file = new File(prefix+sufix);
        System.out.println("File exists? " + Files.exists(filePath));
        mime_type = URLConnection.guessContentTypeFromName(file.getName());
        data = encodeFileToBase64Binary(file);
        name = file.getName();
    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }
}
