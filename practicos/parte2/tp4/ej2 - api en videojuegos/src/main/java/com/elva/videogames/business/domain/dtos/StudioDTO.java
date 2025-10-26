package com.elva.videogames.business.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudioDTO {
    private Long id;
    private String name;
    private boolean inactive;
}

