package com.example.danin.teste;

import java.util.List;

public class Show {

    private int id;
    private String title;
    private List<Integer> category;
    private String description;
    private String poster;

    public Show(int id, String title, List<Integer> category, String description, String poster) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.description = description;
        this.poster = poster;
    }
    /*  results": [
        {
            "original_name": "The Big Bang Theory",
                "genre_ids": [
            35
          ],
            "name": "The Big Bang Theory",
                "popularity": 333.578617,
                "origin_country": [
            "US"
          ],
            "vote_count": 2863,
                "first_air_date": "2007-09-24",
                "backdrop_path": "/nGsNruW3W27V6r4gkyc3iiEGsKR.jpg",
                "original_language": "en",
                "id": 1418,
                "vote_average": 6.8,
                "overview": "The Big Bang Theory is centered on five characters living in Pasadena, California: roommates Leonard Hofstadter and Sheldon Cooper; Penny, a waitress and aspiring actress who lives across the hall; and Leonard and Sheldon's equally geeky and socially awkward friends and co-workers, mechanical engineer Howard Wolowitz and astrophysicist Raj Koothrappali. The geekiness and intellect of the four guys is contrasted for comic effect with Penny's social skills and common sense.",
                "poster_path": "/ooBGRQBdbGzBxAVfExiO8r7kloA.jpg"
        */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getCategory() {
        return category;
    }

    public void setCategory(List<Integer> category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
