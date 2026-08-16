package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product addProduct(Product product, Long categoryId) {
        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException("Product already exists");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product addProduct(Product product, Long categoryId, MultipartFile image) throws IOException {
        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException("Product already exists");
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (image != null && !image.isEmpty()) {
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setImageData(image.getBytes()); // Convert uploaded file to byte[] for DB storage
        }

        product.setCategory(category);

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());

        if (updatedProduct.getImageData() != null) {
            product.setImageName(updatedProduct.getImageName());
            product.setImageType(updatedProduct.getImageType());
            product.setImageData(updatedProduct.getImageData());
        }

        return productRepository.save(product);
    }

    public String deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        return "Product deleted successfully";
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> searchByCategory(String category) {
        return productRepository.findByCategoryNameIgnoreCase(category);
    }

    public Page<Product> getProducts(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return productRepository.findAll(pageable);
    }
}