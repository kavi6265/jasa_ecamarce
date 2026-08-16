package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/upload/{categoryId}", consumes = "multipart/form-data")
    public Product uploadProduct(
            @PathVariable Long categoryId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam Integer stock,
            @RequestParam MultipartFile image
    ) throws IOException {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);

        return productService.addProduct(product, categoryId, image);
    }

    // Endpoint to fetch the raw image directly from the database
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        if (product != null && product.getImageData() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(
                product.getImageType() != null 
                    ? MediaType.parseMediaType(product.getImageType()) 
                    : MediaType.IMAGE_JPEG
            );

            return new ResponseEntity<>(product.getImageData(), headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productService.searchProducts(name);
    }

    @GetMapping("/category")
    public List<Product> categoryProducts(@RequestParam String category) {
        return productService.searchByCategory(category);
    }

    @GetMapping("/page")
    public Page<Product> getProducts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return productService.getProducts(page, size, sortBy);
    }
}