package net.oussa.backend.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class Helper {

    public static String saveImage(MultipartFile file,String serverAddress ) throws IOException {
        String uploadDir="src/main/resources/static/images/";
        // Create the images folder if it doesn't exist

        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Generate a unique file name
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "");

        // Construct the path to save the file
        Path filePath = Path.of(uploadDir, fileName);

        // Copy the file to the target location
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path (if needed) or the full path
        return "http://"+serverAddress+":8084/api/course/images/"+fileName;  // You might want to return the relative path based on your application's needs
    }
}
