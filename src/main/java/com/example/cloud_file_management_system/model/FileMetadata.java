package com.example.cloud_file_management_system.model;

public class FileMetadata {

    private String fileId;
    private String fileName;
    private long fileSize;
    private String uploadTime;

    // Default Constructor
    public FileMetadata() {
    }

    // Parameterized Constructor
    public FileMetadata(String fileId, String fileName, long fileSize, String uploadTime) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }

    // Getters and Setters
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
