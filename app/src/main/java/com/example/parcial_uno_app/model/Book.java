package com.example.parcial_uno_app.model;

public class Book {
    private String title;
    private String author;
    private String description;
    private String price;
    private int coverImage;
    private String category;

    public Book(String title, String author, String description, String price, int coverImage, String category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.coverImage = coverImage;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public int getCoverImage() {
        return coverImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}