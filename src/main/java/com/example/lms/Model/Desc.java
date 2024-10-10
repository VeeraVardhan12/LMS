package com.example.lms.Model;

public class Desc {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Desc{" +
                "description='" + description + '\'' +
                '}';
    }
}
