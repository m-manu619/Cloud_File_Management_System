package com.example.cloud_file_management_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    private final S3Client s3Client;
    private final String bucketName;

    public FileService(@Value("${aws.s3.bucket-name}") String bucketName,
                       @Value("${aws.region}") String region,
                       @Value("${aws.access-key-id}") String accessKeyId,
                       @Value("${aws.secret-access-key}") String secretAccessKey) {
        this.bucketName = bucketName;
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
    }

    // Upload a file to S3 and store its metadata
    public String uploadFile(MultipartFile file) throws IOException {
        String fileId = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();

        // Upload file to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileId)
                .contentType(file.getContentType())  // Set content type from the file
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return fileId;
    }

    // Download a file from S3 by its fileId
    public byte[] downloadFile(String fileId) {
        try (ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileId)
                        .build());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = s3Object.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error reading file from S3", e);
        }
    }

    // Get the content type of the file stored in S3 by fileId
    public String getFileContentType(String fileId) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileId)
                    .build();

            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            return response.contentType();  // Returns the content type (MIME type)
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving file metadata from S3", e);
        }
    }
}
