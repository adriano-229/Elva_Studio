package com.projects.mycar.mycar_cliente.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
    private Long id;

    @Builder.Default
    private boolean eliminado = false;

}
