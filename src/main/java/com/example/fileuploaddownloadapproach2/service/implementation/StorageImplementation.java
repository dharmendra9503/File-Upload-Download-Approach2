package com.example.fileuploaddownloadapproach2.service.implementation;

import com.example.fileuploaddownloadapproach2.model.FileData;
import com.example.fileuploaddownloadapproach2.repository.FileDataRepository;
import com.example.fileuploaddownloadapproach2.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class StorageImplementation implements StorageService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Value("${project.file}")
    private String FOLDER_PATH;

    @Override
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {

        String filePath= FOLDER_PATH+file.getOriginalFilename();

        Optional<FileData> fileData = fileDataRepository.findByName(file.getOriginalFilename());

        if(fileData.isEmpty()) {

            fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath).build());

            File dir = new File(FOLDER_PATH);
            if(!dir.exists()){
                dir.mkdir();
            }

            Files.copy(file.getInputStream(), Paths.get(filePath));

//          file.transferTo(new File(FOLDER_PATH));

            return file.getOriginalFilename();
        }
        else{
            return null;
        }
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);

//        String filePath;
//        if(fileData.isPresent()){
//            filePath=fileData.get().getFilePath();
//        }
//        else{
//            filePath = null;
//        }

        //This is a replacement of above commented code
        String filePath = fileData.map(FileData::getFilePath).orElse(null);

        if(filePath != null) {
//            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return Files.readAllBytes(new File(filePath).toPath());
        }

        return null;
    }
}
