package com.home.eschool.services;

import com.home.eschool.entity.Files;
import com.home.eschool.models.payload.FilesPayload;
import com.home.eschool.repository.FilesRepo;
import com.home.eschool.s3.S3Buckets;
import com.home.eschool.s3.S3Service;
import com.home.eschool.utils.Settings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class FilesService {

    private final FilesRepo filesRepo;
    private final S3Service s3Service;

    private final S3Buckets s3Buckets;

    public FilesService(FilesRepo filesRepo, S3Service s3Service, S3Buckets s3Buckets) {
        this.filesRepo = filesRepo;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public boolean isAvailableTypes(String contentType) {
        return contentType.contains("image/");
    }

    public FilesPayload upload(MultipartFile file) throws IOException {
        Files files = new Files();
        files.setId(UUID.randomUUID());
        files.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        files.setCreateUser(Settings.getCurrentUser());
//        files.setContent(file.getBytes());
        files.setName(file.getOriginalFilename());
        files.setMimeType(file.getContentType());
        files = filesRepo.save(files);
        uploadStudentProfileImage(files.getId(), file);
        return new FilesPayload(files.getId(), files.getName(),
                String.format("/download/%s", files.getId()),
                String.format("data:%s;base64,%s", files.getMimeType(),
                        Base64.getEncoder().encodeToString(getStudentProfileImage(files.getId())))
        );
    }

    public Files getFileById(UUID fileId) {

        Files files = filesRepo.findById(fileId).orElse(null);

        if (files == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "File not found");
        }

        return files;
    }

    public FilesPayload getFileInfo(UUID id) {
        if (id == null) {
            return null;
        }
        Files files = filesRepo.findById(id).orElse(null);
        if (files == null) {
            return null;
        }
        if(files.getContent()!=null){
            return new FilesPayload(files.getId(), files.getName(),
                    String.format("/download/%s", files.getId()),
                    String.format("data:%s;base64,%s", files.getMimeType(),
                            Base64.getEncoder().encodeToString(files.getContent()))
            );
        }
        return new FilesPayload(files.getId(), files.getName(),
                String.format("/download/%s", files.getId()),
                String.format("data:%s;base64,%s", files.getMimeType(),
                        Base64.getEncoder().encodeToString(getStudentProfileImage(id)))
        );
    }

    public void uploadStudentProfileImage(UUID student_id, MultipartFile file) {
        try {
            s3Service.putObject(
                    s3Buckets.getUser(),
                    "profile-images/%s".formatted(student_id),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getStudentProfileImage(UUID student_id) {
        return s3Service.getObject(
                s3Buckets.getUser(),
                "profile-images/%s".formatted(student_id)
        );
    }
}
