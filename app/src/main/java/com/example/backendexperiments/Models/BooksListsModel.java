package com.example.backendexperiments.Models;

public class BooksListsModel {
    private String listName;
    private String displayName;
    private String listNameEncoded;
    private String oldest_published_date;
    private String newest_published_date;
    private String updated;

    public String getListName() {
        return listName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getListNameEncoded() {
        return listNameEncoded;
    }

    public String getOldest_published_date() {
        return oldest_published_date;
    }

    public String getNewest_published_date() {
        return newest_published_date;
    }

    public String getUpdated() {
        return updated;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setListNameEncoded(String listNameEncoded) {
        this.listNameEncoded = listNameEncoded;
    }

    public void setOldest_published_date(String oldest_published_date) {
        this.oldest_published_date = oldest_published_date;
    }

    public void setNewest_published_date(String newest_published_date) {
        this.newest_published_date = newest_published_date;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

}
