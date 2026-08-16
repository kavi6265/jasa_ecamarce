package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    private BannerRepository bannerRepository;

    // 1. Upload Banner Image (Configured for Swagger UI File Picker)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadBanner(
            @RequestParam("title") String title,
            @RequestPart("image") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select an image file.");
            }

            Banner banner = new Banner(
                title,
                file.getBytes(),
                file.getContentType()
            );
            
            Banner savedBanner = bannerRepository.save(banner);

            Map<String, Object> response = new HashMap<>();
            response.put("id", savedBanner.getId());
            response.put("title", savedBanner.getTitle());
            response.put("message", "Banner uploaded successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving banner: " + e.getMessage());
        }
    }

    // 2. Get List of All Banners (IDs & Titles)
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllBanners() {
        List<Map<String, Object>> banners = bannerRepository.findAll().stream().map(banner -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", banner.getId());
            map.put("title", banner.getTitle());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(banners);
    }

    // 3. Serve Raw Banner Image Bytes
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getBannerImage(@PathVariable Long id) {
        return bannerRepository.findById(id)
                .map(banner -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(
                                banner.getImageType() != null ? banner.getImageType() : "image/jpeg"
                        ))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"banner_" + id + "\"")
                        .body(banner.getImageData()))
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Delete Banner
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBanner(@PathVariable Long id) {
        if (bannerRepository.existsById(id)) {
            bannerRepository.deleteById(id);
            return ResponseEntity.ok("Banner deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}