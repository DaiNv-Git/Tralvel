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
        String uploadDir ="/home/user/Travel/BE/images/";;
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        return "/images/" + fileName;
    }
    public static void deleteOldImage(String oldImageUrl) {
        if (oldImageUrl != null && !oldImageUrl.isEmpty()) {
            String uploadDir = "path/to/upload/directory/"; // Đặt đúng đường dẫn thư mục upload
            String oldFileName = oldImageUrl.replace("/images/", "");
            File oldFile = new File(uploadDir + oldFileName);

            if (oldFile.exists()) {
                if (oldFile.delete()) {
                    System.out.println("Deleted old image file: " + oldFileName);
                } else {
                    throw new RuntimeException("Failed to delete old image file: " + oldFileName);
                }
            }
        }
    }
}
