# Cloud File Management System

## Overview

The **Cloud File Management System** is a web application that allows users to upload, download, and manage files in the cloud using **AWS S3**. The system is built using **Spring Boot**, **AWS SDK**, and **Docker**. It provides users with a secure and efficient way to store and retrieve files remotely.

## Features

- **File Upload**: Allows users to upload files (PDF, Word, text, etc.) to the cloud.
- **File Download**: Enables users to download any uploaded file from the cloud.
- **File Management**: View, delete, and manage uploaded files.
- **AWS S3 Integration**: Uses Amazon S3 for cloud storage of files.
- **AWS DynamoDB**: Stores metadata of the uploaded files such as file name, type, and size.
- **User Authentication**: Secure file access and management (can be added with additional authentication mechanism).

## Tech Stack

- **Backend**: 
  - **Spring Boot** (Java)
  - **AWS SDK** for S3 and DynamoDB
  - **JPA** for database interactions (if required)
  - **Docker** for containerization
- **Cloud Services**:
  - **Amazon S3** for cloud file storage
  - **Amazon DynamoDB** for storing file metadata
- **Frontend**: (if applicable, add your frontend tech stack)
  
## Prerequisites

Before running this project locally, ensure that you have the following installed:

- **Java 17+**
- **Maven** (or Gradle, depending on your build tool)
- **Docker** (for running the application in a container)
- **AWS CLI** for configuring AWS credentials
- **AWS Account** with access to S3 and DynamoDB
- **Git** for version control

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/m-manu619/Cloud_File_Management_System.git
cd Cloud_File_Management_System
