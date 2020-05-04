package com.example.backendexperiments.Models;

public class BooksOverviewModel {
    private String author;
    private String title;
    private String description;
    private String createdDate;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
