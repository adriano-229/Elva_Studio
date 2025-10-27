package com.adriano.library.business.logic.service;

import com.adriano.library.business.domain.entity.Book;
import com.adriano.library.business.persistence.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends BaseService<Book, Long> {

    private final BookRepository bookRepository;

    public BookService(BookRepository repository) {
        super(repository);
        this.bookRepository = repository;
    }

    public List<Book> findAllByPublicationYear(int publicationYear) {
        return bookRepository.findAllByPublicationYearOrderByPublicationYear(publicationYear);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void save(Book entity) {
        super.save(entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void update(Long id, Book entity) {
        super.update(id, entity);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void beforeSave(Book entity) {
        // Initialize loanedCopies to 0 for new books
        if (entity.getLoanedCopies() == null) {
            entity.setLoanedCopies(0);
        }
    }

    @Override
    public void beforeUpdate(Long id, Book entity) {
        // Preserve loanedCopies on update (it's managed by LoanService)
        if (entity.getLoanedCopies() == null) {
            findById(id).ifPresent(existing ->
                    entity.setLoanedCopies(existing.getLoanedCopies())
            );
        }
    }
}

