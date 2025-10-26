package com.elva.videogames.business.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideogameDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private short stock;
    private Date releaseDate;
    private boolean inactive;
    private Long studioId;
    private String studioName;
    private Long genreId;
    private String genreName;
}
