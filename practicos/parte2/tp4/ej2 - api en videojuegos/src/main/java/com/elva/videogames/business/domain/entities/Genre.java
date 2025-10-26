package com.elva.videogames.business.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Genre extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Videogame> videogames;

}