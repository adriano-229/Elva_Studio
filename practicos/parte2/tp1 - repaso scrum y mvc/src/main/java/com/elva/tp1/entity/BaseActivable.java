package com.elva.tp1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseActivable implements Activable {
    @Column(nullable = false)
    protected Boolean activo = true;

    @Override
    public boolean isActivo() {
        return Boolean.TRUE.equals(activo);
    }

    @Override
    public void desactivar() {
        this.activo = false;
    }
}

