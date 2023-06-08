package com.blog.app.blog.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public InputStream getResource(String path, String fileName) throws  FileNotFoundException {
        String  fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        
        //file name
        String fileName = file.getOriginalFilename();

        String randomString = UUID.randomUUID().toString();

        //get file extension
        String extension = fileName.substring(fileName.lastIndexOf("."));
    
        //file path
        String filePath = path +File.separator + randomString + extension;

        //create folder is not created
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        //upload file
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return randomString + extension;
    }
    
}
