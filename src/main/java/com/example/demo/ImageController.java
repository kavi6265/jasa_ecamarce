package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {

    @Autowired
    private ImageService service;

    @GetMapping("/")
    public String home() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         Model model) throws IOException {

        Image img=service.upload(file);

        model.addAttribute("id",img.getId());

        return "upload";

    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id){

        Image img=service.get(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(img.getType()))
                .body(img.getImage());

    }

}