package com.josamtechie.controller;

import com.josamtechie.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class StorageController {

    @Autowired
    private StorageService service;

    @PostMapping("image")
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadFile = service.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadFile);
    }

    @GetMapping("image/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName){
        byte[] imageData = service.downloadFile(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping("file")
    public ResponseEntity<?> uploadFileToFileSystem(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadFile = service.uploadFileToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadFile);
    }

    @GetMapping("file/{fileName}")
    public ResponseEntity<?> downloadFileFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] imageData = service.downloadFileFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
