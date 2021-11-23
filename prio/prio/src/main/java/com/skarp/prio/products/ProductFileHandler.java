package com.skarp.prio.products;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProductFileHandler {
    File myFile;
    Path path;

    public File getMyFile() {
        return myFile;
    }

    public Path getPath() {
        return path;
    }

    public void createFile(String FILE_DIRECTORY, MultipartFile multipart) throws IOException {
        // Create a new file path
        File myFile = new File(FILE_DIRECTORY+multipart.getOriginalFilename());
        myFile.createNewFile(); // Create new file with abs path

        // Create File Output Stream with new file
        FileOutputStream fos = new FileOutputStream(myFile);

        // File get bytes and write to new file
        fos.write(multipart.getBytes());

        // Close file
        fos.close();

        this.myFile = myFile; // Add myFile
        this.path = Paths.get(myFile.getAbsolutePath());
    }

    public void deleteFile() {
        try {
            Files.deleteIfExists(this.myFile.toPath());
            System.out.println("File deleted after usage");
        } catch (Exception e) {
            System.out.println("File couldn't be removed");
        }
    }
}
