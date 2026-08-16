package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "banners")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;

    private String imageType;

    public Banner() {}

    public Banner(String title, byte[] imageData, String imageType) {
        this.title = title;
        this.imageData = imageData;
        this.imageType = imageType;
    }

    // Getters and Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }
    public void setTitle(String title) { 
        this.title = title; 
    }

    public byte[] getImageData() { 
        return imageData; 
    }
    public void setImageData(byte[] imageData) { 
        this.imageData = imageData; 
    }

    public String getImageType() { 
        return imageType; 
    }
    public void setImageType(String imageType) { 
        this.imageType = imageType; 
    }
}