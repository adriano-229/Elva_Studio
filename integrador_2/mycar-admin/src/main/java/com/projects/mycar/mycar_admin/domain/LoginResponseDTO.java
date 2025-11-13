package com.projects.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {

    private String token;
    private boolean requiereCambioClave;
}
