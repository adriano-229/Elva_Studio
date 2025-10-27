package com.adriano.library.business.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Book extends BaseEntity {

    private String title;
    private String isbn;
    private Integer totalCopies;
    private Integer loanedCopies;
    private Integer publicationYear;
    private String imagePath;

    @ManyToMany
    private List<Author> authors = new ArrayList<>();

    @ManyToMany
    private List<Publisher> publishers = new ArrayList<>();

    // Transient means not persisted in DB - calculated on the fly
    @Transient
    public Integer getAvailableCopies() {
        return totalCopies - loanedCopies;
    }
}
