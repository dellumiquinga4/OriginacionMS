package com.banquito.originacion.service;

import com.banquito.originacion.controller.dto.VendedorDTO;
import com.banquito.originacion.controller.mapper.VendedorMapper;
import com.banquito.originacion.enums.EstadoVendedorEnum;
import com.banquito.originacion.exception.CreateEntityException;
import com.banquito.originacion.exception.ResourceNotFoundException;
import com.banquito.originacion.exception.UpdateEntityException;
import com.banquito.originacion.model.Vendedor;
import com.banquito.originacion.repository.VendedorRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class VendedorService {

    private final VendedorRepository repository;
    private final VendedorMapper mapper;

    public VendedorService(VendedorRepository repository,
            VendedorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Obtiene un vendedor por su ID.
     */

    @Transactional(readOnly = true)
    public VendedorDTO findById(Integer id) {
        Vendedor entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vendedor no encontrado con id=" + id));
        return mapper.toDTO(entity);
    }

    /**
     * Obtiene todos los vendedores registrados en el sistema.
     */

    @Transactional(readOnly = true)
    public List<VendedorDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los vendedores asociados a un concesionario específico.
     */

    @Transactional(readOnly = true)
    public List<VendedorDTO> findByConcesionario(Integer idConcesionario) {
        return repository.findByIdConcesionario(idConcesionario).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los vendedores por su estado.
     */

    @Transactional(readOnly = true)
    public List<VendedorDTO> findByEstado(EstadoVendedorEnum estado) {
        return repository.findByEstado(estado).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo vendedor en el sistema.
     */

    @Transactional
    public VendedorDTO createVendedor(@Valid VendedorDTO dto) {
        try {
            if (repository.existsByEmail(dto.getEmail())) {
                throw new CreateEntityException(
                        "Vendedor",
                        "Ya existe un vendedor con el mismo email: " + dto.getEmail());
            }
            if (repository.existsByTelefono(dto.getTelefono())) {
                throw new CreateEntityException(
                        "Vendedor",
                        "Ya existe un vendedor con el mismo teléfono: " + dto.getTelefono());
            }

            Vendedor entity = mapper.toModel(dto);
            entity.setId(null);
            entity.setVersion(null);
            Vendedor saved = repository.save(entity);
            return mapper.toDTO(saved);

        } catch (CreateEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new CreateEntityException(
                    "Vendedor",
                    "Error al crear el vendedor. Detalle: " + e.getMessage());
        }
    }

    /**
     * Actualiza un vendedor existente en el sistema.
     */

    @Transactional
    public VendedorDTO updateVendedor(Integer id, VendedorDTO dto) {
        try {
            Vendedor existing = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vendedor no encontrado con id=" + id));

            existing.setIdConcesionario(dto.getIdConcesionario());
            existing.setNombre(dto.getNombre());
            existing.setTelefono(dto.getTelefono());
            existing.setEmail(dto.getEmail());
            existing.setEstado(dto.getEstado());
            existing.setVersion(dto.getVersion());

            Vendedor updated = repository.save(existing);
            return mapper.toDTO(updated);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateEntityException(
                    "Vendedor",
                    "Error al actualizar el vendedor. Detalle: " + e.getMessage());
        }
    }

}
