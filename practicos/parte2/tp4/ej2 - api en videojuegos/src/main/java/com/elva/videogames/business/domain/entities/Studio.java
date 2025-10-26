package com.elva.videogames.business.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
public class Studio extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "studio")
    @JsonIgnore
    private List<Videogame> videogames;

}