package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    public Image upload(MultipartFile file) throws IOException{

        Image img=new Image();

        img.setName(file.getOriginalFilename());
        img.setType(file.getContentType());
        img.setImage(file.getBytes());

        return repository.save(img);

    }

    public Image get(Long id){

        return repository.findById(id).orElse(null);

    }

}