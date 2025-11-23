package org.example.flowerapp.Services;

import org.example.flowerapp.DTO.MaintenanceRequestDTO;
import org.example.flowerapp.DTO.MaintenanceResponseDTO;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.FlowerNotFoundException;
import org.example.flowerapp.Exceptions.EntityNotFoundExceptions.MaintenanceNotFoundException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidMaintenanceDataException;
import org.example.flowerapp.Models.Enums.MaintenanceType;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Models.Maintenance;
import org.example.flowerapp.Repository.FlowerRepository;
import org.example.flowerapp.Repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service layer for managing Maintenance tasks.
 * Handles business logic, validation, and data transformation for flower maintenance tracking.
 */
@Service
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Transactional
    public MaintenanceResponseDTO addNewMaintenance(MaintenanceRequestDTO dto) {
        validateMaintenanceData(dto);
        Flower flower = findFlowerByIdOrThrow(dto.getFlower_id());

        Maintenance maintenance = new Maintenance();
        maintenance.setFlower(flower);
        maintenance.setTaskType(dto.getMaintenanceType());
        maintenance.setScheduledDate(dto.getMaintenanceDate());
        maintenance.setNotes(dto.getNotes());
        maintenance.setPerformedBy(dto.getPerformedBy());
        maintenance.setCreatedAt(LocalDateTime.now());

        Maintenance saved = maintenanceRepository.save(maintenance);

        return mapToResponseDTO(saved);
    }

    @Transactional
    public MaintenanceResponseDTO updateMaintenance(MaintenanceRequestDTO dto, long taskId) {
        Maintenance maintenance = findMaintenanceByIdOrThrow(taskId);
        validateMaintenanceData(dto);
        Flower flower = findFlowerByIdOrThrow(dto.getFlower_id());

        maintenance.setFlower(flower);
        maintenance.setTaskType(dto.getMaintenanceType());
        maintenance.setScheduledDate(dto.getMaintenanceDate());
        maintenance.setNotes(dto.getNotes());
        maintenance.setPerformedBy(dto.getPerformedBy());
        // Keep original createdAt, don't update it

        Maintenance saved = maintenanceRepository.save(maintenance);

        return mapToResponseDTO(saved);
    }

    public MaintenanceResponseDTO getMaintenanceById(long taskId) {
        Maintenance maintenance = findMaintenanceByIdOrThrow(taskId);
        return mapToResponseDTO(maintenance);
    }

    public List<MaintenanceResponseDTO> getAllMaintenance() {
        return maintenanceRepository.findAllMaintenance()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<MaintenanceResponseDTO> getMaintenanceByFlowerId(long flowerId) {
        return maintenanceRepository.findByFlowerId(flowerId)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<MaintenanceResponseDTO> getMaintenanceByType(MaintenanceType maintenanceType) {
        return maintenanceRepository.findByMaintenanceType(maintenanceType)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public List<MaintenanceResponseDTO> getMaintenanceByDate(LocalDateTime dateTime) {
        return maintenanceRepository.findByMaintenanceDate(dateTime)
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional
    public void deleteMaintenance(long taskId) {
        findMaintenanceByIdOrThrow(taskId);
        boolean deleted = maintenanceRepository.deleteMaintenance(taskId);

        if (!deleted) {
            throw new MaintenanceNotFoundException(taskId);
        }
    }

    private void validateMaintenanceData(MaintenanceRequestDTO dto) {
        if (dto.getFlower_id() <= 0) {
            throw new InvalidMaintenanceDataException("Flower ID is required");
        }

        if (dto.getMaintenanceType() == null) {
            throw new InvalidMaintenanceDataException("Maintenance type is required");
        }

        if (dto.getMaintenanceDate() == null) {
            throw new InvalidMaintenanceDataException("Scheduled date is required");
        }

        if (dto.getMaintenanceDate().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new InvalidMaintenanceDataException("Scheduled date cannot be in the past");
        }

        if (dto.getPerformedBy() == null || dto.getPerformedBy().isBlank()) {
            throw new InvalidMaintenanceDataException("Performed by field is required");
        }
    }

    private Flower findFlowerByIdOrThrow(long flowerId) {
        if (!flowerRepository.existsById(flowerId)) {
            throw new FlowerNotFoundException("Flower with id " + flowerId + " not found");
        }
        return flowerRepository.findByFlowerId(flowerId);
    }

    private Maintenance findMaintenanceByIdOrThrow(long taskId) {
        try {
            return maintenanceRepository.findByTaskId(taskId);
        } catch (MaintenanceNotFoundException e) {
            throw new MaintenanceNotFoundException("Maintenance task with id " + taskId + " not found");
        }
    }

    private MaintenanceResponseDTO mapToResponseDTO(Maintenance maintenance) {
        MaintenanceResponseDTO dto = new MaintenanceResponseDTO();

        dto.setTask_id(maintenance.getTask_id());
        dto.setFlower_id(maintenance.getFlower().getFlower_id());
        dto.setMaintenanceType(maintenance.getTaskType());
        dto.setMaintenanceDate(maintenance.getScheduledDate());
        dto.setNotes(maintenance.getNotes());
        dto.setPerformedBy(maintenance.getPerformedBy());
        dto.setCreatedAt(maintenance.getCreatedAt());

        return dto;
    }
}