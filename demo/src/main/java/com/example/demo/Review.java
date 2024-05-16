package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    String comment;
    int rating;

    public String getComment() {
        return comment;
    }

    public int getRating() {
    return rating;}
}
