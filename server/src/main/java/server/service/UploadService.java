package server.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {
    public void upload(MultipartFile... file){
        for (MultipartFile multipartFile : file) {
            try {
                multipartFile.transferTo(new File("C:\\Users\\User\\Desktop\\" + multipartFile.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
