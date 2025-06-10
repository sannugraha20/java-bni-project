package com.bni.bni.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Logic to save the file to the upload directory
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String fileName = StringUtils.cleanPath(originalFilename != null ? originalFilename : "uploaded_file");
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/api/files/" + fileName;

            return ResponseEntity.ok().body(
                    Map.of(
                           "status", 200,
                        "message", "File uploaded successfully",
                        "fileName", fileName,
                        "fileUrl", fileUrl
            )
            );
        } catch (IOException e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "status", 500,
                            "message", "Failed to upload file: " + e.getMessage()
                    )
            );
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<?> getFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(404).body(
                        Map.of(
                                "status", 404,
                                "message", "File not found"
                        )
                );
            }

            byte[] fileContent = Files.readAllBytes(filePath);
            return ResponseEntity.ok().body(
                    Map.of(
                            "status", 200,
                            "fileName", fileName,
                            "fileContent", fileContent
                    )
            );
        } catch (IOException e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "status", 500,
                            "message", "Failed to retrieve file: " + e.getMessage()
                    )
            );
        }
    }
    


}