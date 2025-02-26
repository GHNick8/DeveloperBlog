package com.project.blog.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/media")
public class FileUploadController {

    /* To include images or other media when creating blog posts, 
    add endpoints that can handle file uploads. 
    Use "Spring’s MultipartFile" */ 

    // Directory where uploaded files will be saved 
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure the upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // Save the file locally
            Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
            
            // Return the file path or a URL for accessing the file
            return new ResponseEntity<>("File uploaded successfully: " + filePath.toString(), HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>("File upload failed: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
