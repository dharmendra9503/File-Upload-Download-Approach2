package com.example.fileuploaddownloadapproach2.controller;

import com.example.fileuploaddownloadapproach2.service.StorageService;
import com.example.fileuploaddownloadapproach2.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StorageService storageService;

    @GetMapping("")
    public String welcome(){
        return "Welcome to File Upload Download module";
    }

    @PostMapping("/upload")
    public ResponseEntity<Response> upload(@RequestParam("file") MultipartFile file) throws IOException {

        String fileName;
        try {
            fileName = storageService.uploadImageToFileSystem(file);

            if(fileName != null){
                return new ResponseEntity<>(new Response(fileName, "File Uploaded Successfully !!"),
                        HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(new Response(file.getOriginalFilename(), "File Already Exist !!"),
                        HttpStatus.ALREADY_REPORTED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Response(null, "Internal Server Error, File Not Uploaded"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) throws IOException {
        byte[] imageData=storageService.downloadImageFromFileSystem(fileName);

        if(imageData != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(MediaType.IMAGE_PNG_VALUE))
                    .body(imageData);
        }
        else{
            return new ResponseEntity<>(new Response(fileName, "File doesn't Exist !!"),
                    HttpStatus.NOT_FOUND);
        }
    }
}
