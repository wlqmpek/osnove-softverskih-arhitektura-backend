package ftn.project.support;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

public class ImageSave {

    private static String imagePath = "Front/osnove-softverskih-arhitektura-front/public/pictures";

    public static String saveImage(MultipartFile file) throws IOException {
        Path copyLocation = Paths.get(imagePath + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        String path = "";

        String regex = Pattern.quote(System.getProperty("file.separator"));
        String[] split = copyLocation.toString().split(regex);
        path = "/" + split[3] + "/" + split[4];
        return path;
    }
}
