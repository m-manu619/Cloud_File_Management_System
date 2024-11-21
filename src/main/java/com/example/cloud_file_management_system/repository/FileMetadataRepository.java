package com.example.cloud_file_management_system.repository;

import com.example.cloud_file_management_system.model.FileMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileMetadataRepository {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public FileMetadataRepository(@Value("${aws.dynamodb.table-name}") String tableName,
                                  @Value("${aws.region}") String region,
                                  @Value("${aws.access-key-id}") String accessKeyId,
                                  @Value("${aws.secret-access-key}") String secretAccessKey) {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .credentialsProvider(
                        software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(
                                software.amazon.awssdk.auth.credentials.AwsBasicCredentials.create(accessKeyId, secretAccessKey)))
                .build();
        this.tableName = tableName;
    }

    public void saveFileMetadata(FileMetadata fileMetadata) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("fileId", AttributeValue.builder().s(fileMetadata.getFileId()).build());
        item.put("fileName", AttributeValue.builder().s(fileMetadata.getFileName()).build());
        item.put("fileSize", AttributeValue.builder().n(String.valueOf(fileMetadata.getFileSize())).build());
        item.put("uploadTime", AttributeValue.builder().s(fileMetadata.getUploadTime()).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    public FileMetadata getFileMetadata(String fileId) {
        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(Map.of("fileId", AttributeValue.builder().s(fileId).build()))
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

        if (item == null || item.isEmpty()) {
            return null;
        }

        // Extract and convert values from the AttributeValue map
        String retrievedFileId = item.get("fileId").s();
        String fileName = item.get("fileName").s();
        long fileSize = Long.parseLong(item.get("fileSize").n());
        String uploadTime = item.get("uploadTime").s();

        // Create and return FileMetadata object
        return new FileMetadata(retrievedFileId, fileName, fileSize, uploadTime);
    }

}
