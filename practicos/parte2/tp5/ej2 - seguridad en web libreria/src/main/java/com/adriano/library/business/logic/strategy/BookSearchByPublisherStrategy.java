package com.adriano.library.business.logic.strategy;

import com.adriano.library.business.domain.entity.Book;
import com.adriano.library.business.domain.entity.Publisher;
import com.adriano.library.business.logic.service.PublisherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookSearchByPublisherStrategy implements BookSearchStrategy {

    private final PublisherService publisherService;

    public BookSearchByPublisherStrategy(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public List<Book> search(String publisherName) {
        Optional<Publisher> publisher = publisherService.findByName(publisherName);
        if (publisher.isPresent()) {
            return publisher.get().getBooks();
        } else {
            return List.of();
        }
    }
}
