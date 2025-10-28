package com.adriano.library.business.logic.strategy;

import com.adriano.library.business.domain.entity.Book;
import com.adriano.library.business.logic.service.BookService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookSearchByPublicationYearStrategy implements BookSearchStrategy {

    private final BookService bookService;

    public BookSearchByPublicationYearStrategy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public List<Book> search(String publicationYear) {
        try {
            return bookService.findAllByPublicationYear(Integer.parseInt(publicationYear));
        } catch (NumberFormatException e) {
            return List.of();
        }
    }
}
