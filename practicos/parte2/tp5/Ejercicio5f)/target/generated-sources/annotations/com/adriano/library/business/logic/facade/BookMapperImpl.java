package com.adriano.library.business.logic.facade;

import com.adriano.library.business.domain.entity.Book;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-27T23:18:22-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private AuthorMapperHelper authorMapperHelper;
    @Autowired
    private PublisherMapperHelper publisherMapperHelper;

    @Override
    public Book toEntity(BookDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthors( authorMapperHelper.mapAuthorIdsToAuthors( dto.getAuthorIds() ) );
        book.setPublishers( publisherMapperHelper.mapPublisherIdsToPublishers( dto.getPublisherIds() ) );
        book.setTitle( dto.getTitle() );
        book.setIsbn( dto.getIsbn() );
        book.setTotalCopies( dto.getTotalCopies() );
        book.setLoanedCopies( dto.getLoanedCopies() );
        book.setPublicationYear( dto.getPublicationYear() );
        book.setImagePath( dto.getImagePath() );

        return book;
    }

    @Override
    public BookDTO toDto(Book entity) {
        if ( entity == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setAuthorIds( authorMapperHelper.mapAuthorsToAuthorIds( entity.getAuthors() ) );
        bookDTO.setPublisherIds( publisherMapperHelper.mapPublishersToPublisherIds( entity.getPublishers() ) );
        bookDTO.setTitle( entity.getTitle() );
        bookDTO.setIsbn( entity.getIsbn() );
        bookDTO.setTotalCopies( entity.getTotalCopies() );
        bookDTO.setLoanedCopies( entity.getLoanedCopies() );
        bookDTO.setPublicationYear( entity.getPublicationYear() );
        bookDTO.setImagePath( entity.getImagePath() );

        return bookDTO;
    }
}
