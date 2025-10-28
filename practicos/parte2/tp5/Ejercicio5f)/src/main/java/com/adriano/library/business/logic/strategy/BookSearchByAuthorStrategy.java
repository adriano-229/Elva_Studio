package com.adriano.library.business.logic.strategy;

import com.adriano.library.business.domain.entity.Author;
import com.adriano.library.business.domain.entity.Book;
import com.adriano.library.business.logic.service.AuthorService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookSearchByAuthorStrategy implements BookSearchStrategy {
    private final AuthorService authorService;


    public BookSearchByAuthorStrategy(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public List<Book> search(String authorName) {
        Optional<Author> author = authorService.findByName(authorName);
        if (author.isPresent()) {
            return author.get().getBooks();
        } else {
            return List.of();
        }
    }
}
