package com.projects.mycar.mycar_cliente.domain;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public abstract class BaseDTO {
	private Long id;
	
	@Builder.Default
	private boolean eliminado = false;

}
