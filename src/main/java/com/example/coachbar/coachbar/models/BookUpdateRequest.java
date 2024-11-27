package com.example.coachbar.coachbar.models;


import jakarta.validation.constraints.NotNull;
import lombok.ToString;


@ToString
public class BookUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private Integer publicationYear;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
