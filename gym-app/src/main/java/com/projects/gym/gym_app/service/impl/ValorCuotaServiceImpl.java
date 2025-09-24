package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.ValorCuota;
import com.projects.gym.gym_app.domain.enums.Mes;
import com.projects.gym.gym_app.repository.ValorCuotaRepository;
import com.projects.gym.gym_app.service.ValorCuotaService;
import com.projects.gym.gym_app.service.dto.ValorCuotaDTO;
import com.projects.gym.gym_app.service.dto.ValorCuotaFormDTO;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ValorCuotaServiceImpl implements ValorCuotaService {

    private final ValorCuotaRepository valorCuotaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ValorCuotaDTO> listar() {
        Comparator<ValorCuota> comparator = Comparator
                .comparing(ValorCuota::getAnio).reversed()
                .thenComparing(Comparator.comparingInt((ValorCuota vc) -> vc.getMes().ordinal()).reversed());

        return valorCuotaRepository.findAll().stream()
                .sorted(comparator)
                .map(ValorCuotaServiceImpl::toDto)
                .toList();
    }

    @Override
    public ValorCuotaDTO crear(ValorCuotaFormDTO form) {
        validate(form);

        if (valorCuotaRepository.existsByAnioAndMes(form.getAnio(), form.getMes())) {
            throw new IllegalArgumentException("Ya existe un valor de cuota para el mes seleccionado");
        }

        ValorCuota entidad = new ValorCuota();
        entidad.setMes(form.getMes());
        entidad.setAnio(form.getAnio());
        entidad.setValorCuota(form.getValor());

        ValorCuota guardado = valorCuotaRepository.save(entidad);
        return toDto(guardado);
    }

    private void validate(ValorCuotaFormDTO form) {
        if (form.getMes() == null || form.getAnio() == null) {
            throw new IllegalArgumentException("Mes y año son obligatorios");
        }
        LocalDate ahora = LocalDate.now();
        int mesActualOrdinal = Mes.values()[ahora.getMonthValue() - 1].ordinal();
        int mesFormularioOrdinal = form.getMes().ordinal();
        if (form.getAnio() < ahora.getYear() ||
                (form.getAnio().equals(ahora.getYear()) && mesFormularioOrdinal < mesActualOrdinal)) {
            throw new IllegalArgumentException("El valor de cuota solo puede crearse para el mes vigente o futuros");
        }
        if (form.getValor() == null || form.getValor().signum() <= 0) {
            throw new IllegalArgumentException("El valor de la cuota debe ser mayor a cero");
        }
    }

    private static ValorCuotaDTO toDto(ValorCuota entidad) {
        return ValorCuotaDTO.builder()
                .id(entidad.getId())
                .mes(entidad.getMes())
                .anio(entidad.getAnio())
                .valor(entidad.getValorCuota())
                .creadoEl(entidad.getCreadoEl())
                .build();
    }
}
