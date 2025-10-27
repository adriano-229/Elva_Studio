package com.adriano.library.business.logic.strategy;

import com.adriano.library.business.domain.entity.Book;

import java.util.List;

public interface BookSearchStrategy {

    List<Book> search(String query);
}
