package org.vik;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinIOUploader {
    public static void uploadObjects(String bucketName, String localFolderPath, String minioEndpoint,
                                     String accessKey, String secretKey) {
        // Set up MinIO client
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(accessKey, secretKey)
                .build();


        try {
            // Check if the bucket already exists
            // Check if the bucket already exists
            boolean isBucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
            if (!isBucketExists) {
                // Create the bucket if it doesn't exist
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                System.out.println("Bucket " + bucketName + " created.");
            }

            // Continue with uploading objects...
            // Your existing code for uploading objects goes here

            // Get the list of files in the local folder
            File folder = new File(localFolderPath);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Upload each file to MinIO
                    try (FileInputStream inputStream = new FileInputStream(file)) {
                        minioClient.putObject(
                                PutObjectArgs.builder()
                                        .bucket(bucketName)
                                        .object(file.getName())
                                        .stream(inputStream, file.length(), -1)
                                        .build()
                        );

                        System.out.println("Object " + file.getName() + " uploaded to MinIO.");
                    } catch (MinioException | IOException e) {
                        System.out.println("Error uploading object to MinIO: " + e.getMessage());
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    } catch (InvalidKeyException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (InvalidKeyException | NoSuchAlgorithmException |
                 IOException | MinioException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}