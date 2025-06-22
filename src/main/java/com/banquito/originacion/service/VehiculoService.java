package com.banquito.originacion.service;

import com.banquito.originacion.controller.dto.VehiculoDTO;
import com.banquito.originacion.controller.mapper.VehiculoMapper;
import com.banquito.originacion.enums.EstadoVehiculoEnum;
import com.banquito.originacion.exception.CreateEntityException;
import com.banquito.originacion.exception.DeleteEntityException;
import com.banquito.originacion.exception.ResourceNotFoundException;
import com.banquito.originacion.exception.UpdateEntityException;
import com.banquito.originacion.model.Vehiculo;
import com.banquito.originacion.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    public VehiculoService(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
    }

    @Transactional(readOnly = true)
    public VehiculoDTO findById(Integer id) {
        Vehiculo entidad = vehiculoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Vehículo no encontrado con id=" + id));
        return vehiculoMapper.toDTO(entidad);
    }

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findAll() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los vehículos de un concesionario dado.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findByConcesionario(Integer idConcesionario) {
        return vehiculoRepository.findByIdConcesionario(idConcesionario).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }
    /**
     * Obtiene todos los vehículos de un concesionario dado.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findByEstado(EstadoVehiculoEnum estado) {
        return vehiculoRepository.findByEstado(estado).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene vehículos por marca.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findByMarca(String marca) {
        return vehiculoRepository.findByMarcaIgnoreCase(marca).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene vehículos por modelo.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findByModelo(String modelo) {
        return vehiculoRepository.findByModeloIgnoreCase(modelo).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VehiculoDTO createVehiculo(VehiculoDTO dto) {
        try {
            Vehiculo entidad = vehiculoMapper.toModel(dto);
            entidad.setId(null); 
            entidad.setVersion(null); 
            Vehiculo guardado = vehiculoRepository.save(entidad);
            return vehiculoMapper.toDTO(guardado);

        } catch (Exception e) {
            throw new CreateEntityException(
                "Vehiculo",
                "Error al crear el vehículo. Detalle: " + e.getMessage()
            );
        }
    }

    @Transactional
    public VehiculoDTO updateVehiculo(Integer id, VehiculoDTO dto) {
        try {
            Vehiculo existente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Vehículo no encontrado con id=" + id));

            existente.setIdConcesionario(dto.getIdConcesionario());
            existente.setIdIdentificadorVehiculo(dto.getIdIdentificadorVehiculo());
            existente.setMarca(dto.getMarca());
            existente.setModelo(dto.getModelo());
            existente.setAnio(dto.getAnio());
            existente.setValor(dto.getValor());
            existente.setColor(dto.getColor());
            existente.setExtras(dto.getExtras());
            existente.setEstado(dto.getEstado());
            existente.setVersion(dto.getVersion());

            Vehiculo actualizado = vehiculoRepository.save(existente);
            return vehiculoMapper.toDTO(actualizado);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateEntityException(
                "Vehiculo",
                "Error al actualizar el vehículo. Detalle: " + e.getMessage()
            );
        }
    }

    @Transactional
    public void deleteVehiculo(Integer id) {
        try {
            Vehiculo existente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Vehículo no encontrado con id=" + id));
            vehiculoRepository.delete(existente);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DeleteEntityException(
                "Vehiculo",
                "Error al eliminar el vehículo. Detalle: " + e.getMessage()
            );
        }
    }
    

}
