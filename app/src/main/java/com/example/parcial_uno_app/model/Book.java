package com.example.parcial_uno_app.model;

public class Book {
    private String title;
    private String author;
    private String description;
    private float rating;
    private String price;
    private int coverImage;

    public Book(String title, String author, String description, float rating, String price, int coverImage) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.coverImage = coverImage;
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

    public float getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }

    public int getCoverImage() {
        return coverImage;
    }
}