package kg.nurtelecom.internlabs.taskmanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryService {
    String uploadFile(MultipartFile file, String folderName);
}
