package com.projects.mycar.mycar_admin.domain;

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
    private boolean activo = true;


}
