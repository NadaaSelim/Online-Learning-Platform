package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Instructor {
    public String id;
    public String name;
    public String email;
    public String password;
    public String affiliation;
    public int yearsOfExperience;
    public String bio;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getBio() {
        return bio;
    }


}
