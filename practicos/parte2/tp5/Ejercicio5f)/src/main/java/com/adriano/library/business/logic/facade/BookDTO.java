package com.adriano.library.business.logic.facade;

import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    private String title;
    private String isbn;
    private Integer totalCopies;
    private Integer loanedCopies;
    private Integer publicationYear;
    private String imagePath;
    private List<Long> authorIds;
}
