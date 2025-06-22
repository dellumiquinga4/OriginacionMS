package com.banquito.originacion.service;

import com.banquito.originacion.controller.dto.IdentificadorVehiculoDTO;
import com.banquito.originacion.controller.dto.VehiculoDTO;
import com.banquito.originacion.controller.mapper.IdentificadorVehiculoMapper;
import com.banquito.originacion.controller.mapper.VehiculoMapper;
import com.banquito.originacion.enums.EstadoVehiculoEnum;
import com.banquito.originacion.exception.CreateEntityException;
import com.banquito.originacion.exception.DeleteEntityException;
import com.banquito.originacion.exception.ResourceNotFoundException;
import com.banquito.originacion.exception.UpdateEntityException;
import com.banquito.originacion.model.IdentificadorVehiculo;
import com.banquito.originacion.model.Vehiculo;
import com.banquito.originacion.repository.IdentificadorVehiculoRepository;
import com.banquito.originacion.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class VehiculoService {

    private final IdentificadorVehiculoRepository identificadorRepository;
    private final IdentificadorVehiculoMapper identificadorMapper;
    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    public VehiculoService(IdentificadorVehiculoRepository identificadorRepository,
            IdentificadorVehiculoMapper identificadorMapper,
            VehiculoRepository vehiculoRepository,
            VehiculoMapper vehiculoMapper) {
        this.identificadorRepository = identificadorRepository;
        this.identificadorMapper = identificadorMapper;
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
    }

    // ------------------------------------------- Métodos para IdentificadorVehiculo -------------------------------------------

    /**
     * Obtiene todos los identificadores de vehículos.
     */

    @Transactional(readOnly = true)
    public List<IdentificadorVehiculoDTO> getAllIdentificadores() {
        return identificadorRepository.findAll().stream()
                .map(identificadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un identificador de vehículo por su ID.
     */

    @Transactional(readOnly = true)
    public IdentificadorVehiculoDTO getIdentificadorById(Integer id) {
        IdentificadorVehiculo entity = identificadorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IdentificadorVehiculo no encontrado con id=" + id));
        return identificadorMapper.toDTO(entity);
    }

    /**
     * Crea un nuevo identificador de vehículo.
     */

    @Transactional
    public IdentificadorVehiculoDTO createIdentificador(IdentificadorVehiculoDTO dto) {
        try {
            if (identificadorRepository.existsByVin(dto.getVin())) {
                throw new CreateEntityException(
                        "IdentificadorVehiculo",
                        "Ya existe un identificador con el mismo VIN: " + dto.getVin());
            }
            if (identificadorRepository.existsByNumeroMotor(dto.getNumeroMotor())) {
                throw new CreateEntityException(
                        "IdentificadorVehiculo",
                        "Ya existe un identificador con el mismo número de motor: " + dto.getNumeroMotor());
            }
            if (identificadorRepository.existsByPlaca(dto.getPlaca())) {
                throw new CreateEntityException(
                        "IdentificadorVehiculo",
                        "Ya existe un identificador con la misma placa: " + dto.getPlaca());
            }

            IdentificadorVehiculo entity = identificadorMapper.toModel(dto);
            entity.setId(null);
            entity.setVersion(null);
            IdentificadorVehiculo saved = identificadorRepository.save(entity);
            return identificadorMapper.toDTO(saved);

        } catch (CreateEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new CreateEntityException(
                    "IdentificadorVehiculo",
                    "Error al crear el identificador. Detalle: " + e.getMessage());
        }
    }

    // ------------------------------------------- Métodos para Vehiculo -------------------------------------------

    /**
     * Obtiene el vehículo por su ID.
     */

    @Transactional(readOnly = true)
    public VehiculoDTO findVehiculoById(Integer id) {
        Vehiculo entity = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehículo no encontrado con id=" + id));
        return vehiculoMapper.toDTO(entity);
    }

    /**
     * Obtiene todos los vehículos.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findAllVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los vehículos de un concesionario dado.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findVehiculosByConcesionario(Integer idConcesionario) {
        return vehiculoRepository.findByIdConcesionario(idConcesionario).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los vehículos de un concesionario dado.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findVehiculosByEstado(EstadoVehiculoEnum estado) {
        return vehiculoRepository.findByEstado(estado).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene vehículos por marca.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findVehiculosByMarca(String marca) {
        return vehiculoRepository.findByMarcaIgnoreCase(marca).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene vehículos por modelo.
     */

    @Transactional(readOnly = true)
    public List<VehiculoDTO> findVehiculosByModelo(String modelo) {
        return vehiculoRepository.findByModeloIgnoreCase(modelo).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo vehículo.
     */

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
                    "Error al crear el vehículo. Detalle: " + e.getMessage());
        }
    }

    /**
     * Actualiza un vehículo existente.
     */

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
                    "Error al actualizar el vehículo. Detalle: " + e.getMessage());
        }
    }

    /**
     * Elimina un vehículo por su ID.
     */

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
                    "Error al eliminar el vehículo. Detalle: " + e.getMessage());
        }
    }

}
