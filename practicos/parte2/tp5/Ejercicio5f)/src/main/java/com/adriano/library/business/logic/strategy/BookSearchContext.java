package com.adriano.library.business.logic.strategy;

import com.adriano.library.business.domain.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchContext {
    private final BookSearchByAuthorStrategy authorStrategy;
    private final BookSearchByPublisherStrategy publisherStrategy;
    private final BookSearchByPublicationYearStrategy yearStrategy;

    public BookSearchContext(
            BookSearchByAuthorStrategy authorStrategy,
            BookSearchByPublisherStrategy publisherStrategy,
            BookSearchByPublicationYearStrategy yearStrategy) {
        this.authorStrategy = authorStrategy;
        this.publisherStrategy = publisherStrategy;
        this.yearStrategy = yearStrategy;
    }

    public List<Book> search(SearchType type, String query) {
        return switch (type) {
            case AUTHOR -> authorStrategy.search(query);
            case PUBLISHER -> publisherStrategy.search(query);
            case YEAR -> yearStrategy.search(query);
        };
    }
}
