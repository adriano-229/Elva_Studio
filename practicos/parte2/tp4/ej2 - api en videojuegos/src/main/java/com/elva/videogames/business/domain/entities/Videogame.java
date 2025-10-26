package com.elva.videogames.business.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "videogames")
@Data
public class Videogame extends BaseEntity {

    @NotEmpty(message = "{NotEmpty.Videogame.name}")
    private String name;

    @NotEmpty(message = "Description is required")
    @Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters")
    private String description;

    private String imageUrl;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be zero or positive")
    @Max(value = 1000, message = "Price must be less than or equal to 1000")
    private Double price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be zero or positive")
    @Max(value = 10000, message = "Stock must be less than or equal to 10000")
    private short stock;


    @NotNull(message = "Release date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Release date cannot be in the future")
    private Date releaseDate;

    @NotNull(message = "Studio must be selected")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_studio", nullable = false)
    @JsonIgnore
    private Studio studio;

    @NotNull(message = "Genre must be selected")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_genre", nullable = false)
    @JsonIgnore
    private Genre genre;

}