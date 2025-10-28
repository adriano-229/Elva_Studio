package com.adriano.library.business.logic.facade;

import com.adriano.library.business.domain.entity.Book;
import com.adriano.library.business.logic.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookFacade {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookFacade(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    public void create(BookDTO dto) {
        // 1. Convert DTO → Entity (MapStruct does the heavy lifting)
        Book book = bookMapper.toEntity(dto);

        // 2. (Optional) add any higher-level business logic
        // e.g. validate authors, etc.
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("A book must have at least one author.");
        }

        // 3. Save
        bookService.save(book);
    }

    public void update(Long id, BookDTO dto) {
        // 1. Verify book exists
        Book existingBook = bookService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));

        // 2. Convert DTO → Entity
        Book book = bookMapper.toEntity(dto);
        book.setId(id); // Ensure the ID is preserved

        // 3. Business validation
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("A book must have at least one author.");
        }

        // 4. Update
        bookService.update(id, book);
    }

    public void delete(Long id) {
        bookService.deleteById(id);
    }
}
