package com.example.cloud_file_management_system.controller;

import com.example.cloud_file_management_system.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileId = fileService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + fileId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileId) {
        try {
            // Retrieve the file from S3 based on its fileId
            byte[] fileBytes = fileService.downloadFile(fileId);

            // Retrieve the file's content type (MIME type) from S3 metadata
            String contentType = fileService.getFileContentType(fileId);

            // Set up response headers for downloading the file
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(fileBytes.length);
            headers.setContentType(MediaType.valueOf(contentType));  // Dynamically set content type
            headers.setContentDispositionFormData("attachment", fileId);  // Set the file to download as attachment

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
