package com.blog.app.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.blog.payloads.FileResponse;
import com.blog.app.blog.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(
        @RequestParam("file") MultipartFile image
    ){
        String fileName  = "";
        try {
            fileName =  this.fileService.uploadImage(path, image); 
        } catch (Exception e) {
           e.printStackTrace();
           return new ResponseEntity<>(new FileResponse(null, "Error while uploading"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(new FileResponse(fileName, "File uploaded successfully"), HttpStatus.OK);
    }

    //method to serve file
    @GetMapping(value = "/image/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("fileName") String fileName,
        HttpServletResponse response
    ) throws FileNotFoundException, IOException {
        
        InputStream resource  = this.fileService.getResource(path, fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        FileCopyUtils.copy(resource, response.getOutputStream());
    }


    
}
