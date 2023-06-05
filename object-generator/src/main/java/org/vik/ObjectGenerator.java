package org.vik;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

public class ObjectGenerator {
    private static final String[] FILE_TYPES = {"text", "image", "video"};
    private static final String[] FILE_EXTENSIONS = {".txt", ".jpg", ".mp4"};

    // Function to generate random data of specified size
    private static byte[] generateData(int size) {
        SecureRandom random = new SecureRandom();
        byte[] data = new byte[size];
        random.nextBytes(data);
        return data;
    }

    // Function to create objects and save them to the local filesystem
    public static void createObjects(int numObjects, String outputPath) {
        for (int i = 1; i <= numObjects; i++) {
            // Generate random object size between 1KB and 10MB
            int objectSize = 1024 + (int) (Math.random() * (10 * 1024 * 1024 - 1024));

            // Generate random file type
            int fileTypeIndex = (int) (Math.random() * FILE_TYPES.length);
            String fileType = FILE_TYPES[fileTypeIndex];
            String fileExtension = FILE_EXTENSIONS[fileTypeIndex];

            // Generate random object data
            byte[] objectData = generateData(objectSize);

            // Generate object name with extension
            String objectName = "object" + i + fileExtension;

            // Save object data to the local filesystem
            try {
                File folder = new File(outputPath);
                if (!folder.exists()) {
                    folder.mkdirs(); // Create the folder and any parent folders if they don't exist
                }

                String filePath = outputPath + "/" + objectName;
                try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    outputStream.write(objectData);
                    System.out.println("Object saved to: " + filePath);
                } catch (IOException e) {
                    System.out.println("Error saving object to the local filesystem: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error creating folder: " + e.getMessage());
            }

            System.out.println("Object " + objectName + " created and saved to the local filesystem.");
        }
    }
}
