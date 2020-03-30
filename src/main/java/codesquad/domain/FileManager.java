package codesquad.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileManager {

    @Value("${file.upload.local}")
    private String uploadPath;

    public String upload(MultipartFile file, String fileManageName) throws IOException {
        File saveFile = new File(getUploadPath() + fileManageName);
        file.transferTo(saveFile);
        return fileManageName;
    }

    public FileSystemResource download(String fileManageName) {
        Path path = Paths.get(getUploadPath() + fileManageName);
        return new FileSystemResource(path);
    };

    protected String getUploadPath() {
        return uploadPath;
    }
}
