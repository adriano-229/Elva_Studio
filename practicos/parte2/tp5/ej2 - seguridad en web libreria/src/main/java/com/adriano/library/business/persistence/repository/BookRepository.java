package com.adriano.library.business.persistence.repository;

import com.adriano.library.business.domain.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends BaseRepository<Book, Long> {

    List<Book> findAllByPublicationYearOrderByPublicationYear(int publicationYear);

}

