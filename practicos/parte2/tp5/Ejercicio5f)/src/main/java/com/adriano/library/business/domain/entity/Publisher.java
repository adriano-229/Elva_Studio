package com.adriano.library.business.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Publisher extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "publishers")
    private List<Book> books;
}
