package com.josamtechie.service;

import com.josamtechie.model.FileData;
import com.josamtechie.model.ImageData;
import com.josamtechie.repository.FileDataRepository;
import com.josamtechie.repository.ImageDataRepository;
import com.josamtechie.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class StorageService {

    @Autowired
    private ImageDataRepository repository;

    @Autowired
    private FileDataRepository fileDataRepository;

    private final String FOLDER_PATH="D:/Software/UpDownFiles/";

    public String uploadFile(MultipartFile file) throws IOException {
        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(FileUtils.compressImage(file.getBytes())).build());
        if(imageData != null){
            return "File uploaded successfully : "+file.getOriginalFilename();
        }
        return null;
    }

    public byte[] downloadFile(String fileName){
        Optional<ImageData> dBFileData = repository.findByName(fileName);
        byte[] images = FileUtils.decompressImage(dBFileData.get().getImageData());
        return images;
    }

    public String uploadFileToFileSystem(MultipartFile file) throws IOException {
        String filepath = FOLDER_PATH+file.getOriginalFilename();
        FileData fileData = fileDataRepository.save(FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filepath).build());
        file.transferTo(new File(filepath));

        if(fileData != null){
            return "File uploaded successfully : "+filepath;
        }
        return null;
    }

    public byte[] downloadFileFromFileSystem(String fileName) throws IOException {
        Optional<FileData> dBfilePath = fileDataRepository.findByName(fileName);
        String filePath =  dBfilePath.get().getFilePath();
        byte[] files = Files.readAllBytes(new File(filePath).toPath());
        return files;
    }

}
