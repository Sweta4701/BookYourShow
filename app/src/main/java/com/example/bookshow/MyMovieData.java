package com.example.bookshow;

public class MyMovieData {
    private int image;
    private String name;
    private String language;

    public MyMovieData(String name, String language, int image) {
        this.name = name;
        this.language = language;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String date) {
        this.language = language;
    }

    public int getImage() {
        return image;
    }

    public void setImg(int image) {
        this.image = image;
    }
}
