package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category addCategory(Category category) {

        if (repository.existsByName(category.getName())) {
            throw new RuntimeException("Category already exists");
        }

        return repository.save(category);
    }
}