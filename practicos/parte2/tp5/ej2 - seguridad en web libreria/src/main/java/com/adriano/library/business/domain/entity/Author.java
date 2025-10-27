package com.adriano.library.business.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Author extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
