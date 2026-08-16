package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return service.addCategory(category);
    }
}