package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    List<Product> findByNameContainingIgnoreCase(String name);
   
    List<Product> findByCategoryNameIgnoreCase(String category);
    Page<Product> findAll(Pageable pageable);
}