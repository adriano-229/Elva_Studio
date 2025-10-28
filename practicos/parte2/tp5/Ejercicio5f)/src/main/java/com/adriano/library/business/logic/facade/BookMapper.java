package com.adriano.library.business.logic.facade;

import com.adriano.library.business.domain.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AuthorMapperHelper.class)
public interface BookMapper {

    @Mapping(target = "authors", source = "authorIds")
    Book toEntity(BookDTO dto);

    BookDTO toDto(Book entity);
}