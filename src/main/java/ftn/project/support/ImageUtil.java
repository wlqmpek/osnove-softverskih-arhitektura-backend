package ftn.project.support;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.imageio.ImageIO;

public class ImageUtil {

    private static String imagePath = "Front/osnove-softverskih-arhitektura-front/public/pictures";

    public static String saveImage(MultipartFile file) throws IOException {
        InputStream is = new BufferedInputStream(file.getInputStream());
        System.out.println("Ekstenzija " + URLConnection.guessContentTypeFromStream(is));
        Path copyLocation = Paths.get(imagePath + File.separator + StringUtils.cleanPath(file.getOriginalFilename() + '.' +  URLConnection.guessContentTypeFromStream(is).split("/")[1]));
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        String path = "";

        String regex = Pattern.quote(System.getProperty("file.separator"));
        String[] split = copyLocation.toString().split(regex);
        path = "/" + split[3] + "/" + split[4];
        return path;
    }

//    public static Resource getImage(String imagePath) throws IOException {
//        Path filePath = fileStorageLocation.resolve(imagePath).normalize();
//        Resource resource = new UrlResource(filePath.toUri());
//
//    }
}
