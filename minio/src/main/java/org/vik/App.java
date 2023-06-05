package org.vik;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        // Make sure you create a bucket with test in the minio server
        String bucketName = "test";
        String localFolderPath = "./objects";
//        String minioEndpoint = "http://docker-vik02:9000";
        String minioEndpoint = "http://localhost:9000";
        String accessKey = "your-access-key";
        String secretKey = "your-secret-key";

        MinIOUploader.uploadObjects(bucketName, localFolderPath, minioEndpoint, accessKey, secretKey);

    }
}
