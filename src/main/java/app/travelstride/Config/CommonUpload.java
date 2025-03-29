package app.travelstride.Config;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Component
public class CommonUpload {
    public String saveImage(MultipartFile file) throws IOException {
        String uploadDir = System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf("/")) + "/images/";;
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        return "/images/" + fileName;
    }

}
