package com.banquito.originacion.service;

import com.banquito.originacion.controller.dto.IdentificadorVehiculoDTO;
import com.banquito.originacion.model.IdentificadorVehiculo;
import com.banquito.originacion.repository.IdentificadorVehiculoRepository;
import com.banquito.originacion.controller.mapper.IdentificadorVehiculoMapper;
import com.banquito.originacion.exception.CreateEntityException;
import com.banquito.originacion.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class IdentificadorVehiculoService {

    private final IdentificadorVehiculoRepository repository;
    private final IdentificadorVehiculoMapper mapper;

    public IdentificadorVehiculoService(IdentificadorVehiculoRepository repository,
            IdentificadorVehiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<IdentificadorVehiculoDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IdentificadorVehiculoDTO getById(Integer id) {
        IdentificadorVehiculo entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "IdentificadorVehiculo no encontrado con id=" + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public IdentificadorVehiculoDTO create(IdentificadorVehiculoDTO dto) {
        try {
            if (repository.existsByVin(dto.getVin())) {
                throw new CreateEntityException(
                   "IdentificadorVehiculo",
                   "Ya existe un identificador con el mismo VIN: " + dto.getVin());
            }
            if (repository.existsByNumeroMotor(dto.getNumeroMotor())) {
                throw new CreateEntityException(
                   "IdentificadorVehiculo",
                   "Ya existe un identificador con el mismo número de motor: " + dto.getNumeroMotor());
            }
            if (repository.existsByPlaca(dto.getPlaca())) {
                throw new CreateEntityException(
                   "IdentificadorVehiculo",
                   "Ya existe un identificador con la misma placa: " + dto.getPlaca());
            }

            IdentificadorVehiculo entity = mapper.toModel(dto);
            entity.setId(null);  
            entity.setVersion(null); 
            IdentificadorVehiculo saved = repository.save(entity);
            return mapper.toDTO(saved);

        } catch (CreateEntityException e) {
            // ya es unchecked con código 500 y mensaje formateado
            throw e;
        } catch (Exception e) {
            // cualquier otro fallo en JPA o mapeo
            throw new CreateEntityException(
               "IdentificadorVehiculo",
               "Error al crear el identificador. Detalle: " + e.getMessage());
        }
    }

}
