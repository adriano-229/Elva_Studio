package com.projects.mycar.mycar_admin.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private boolean first;
    private boolean last;

}
