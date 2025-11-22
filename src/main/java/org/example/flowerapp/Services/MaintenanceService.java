package org.example.flowerapp.Services;

import org.example.flowerapp.Models.Enums.MaintenanceType;
import org.example.flowerapp.Models.Maintenance;
import org.example.flowerapp.Repository.MaintenanceRepository;
import org.example.flowerapp.Utils.Validators.MaintenanceValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceService {
    
    private final MaintenanceRepository maintenanceRepository;
    private final FlowerService flowerService;
    
    public MaintenanceService(MaintenanceRepository maintenanceRepository, FlowerService flowerService){
        this.maintenanceRepository = maintenanceRepository;
        this.flowerService = flowerService;
    }

    public Maintenance addMaintenanceTask(Maintenance maintenance) {
        MaintenanceValidator.validateMaintenanceTask(maintenance);
        
        long flowerId = maintenance.getFlower().getFlower_id();
        flowerService.getFlowerById(flowerId);

        if (maintenance.getCreatedAt() == null) {
            maintenance.setCreatedAt(LocalDateTime.now());
        }
        return maintenanceRepository.save(maintenance);
    }

    public Maintenance getTaskById(long id) {
        return maintenanceRepository.findByTaskId(id);
    }

    public List<Maintenance> getAllTasks() {
        return maintenanceRepository.findAllMaintenance();
    }

    public Maintenance updateMaintenanceTask(Maintenance maintenance) {
        Maintenance existingTask = getTaskById(maintenance.getTask_id()); 

        if (maintenance.getTaskType() != null) {
            existingTask.setTaskType(maintenance.getTaskType());
        }
        
        if (maintenance.getScheduledDate() != null) {
            existingTask.setScheduledDate(maintenance.getScheduledDate());
        }
        
        if (maintenance.getNotes() != null) {
            existingTask.setNotes(maintenance.getNotes());
        }
        
        if (maintenance.getPerformedBy() != null) {
            existingTask.setPerformedBy(maintenance.getPerformedBy());
        }

        MaintenanceValidator.validateMaintenanceTask(existingTask);
        return maintenanceRepository.save(existingTask);
    }

    public boolean deleteMaintenance(long id) {
        getTaskById(id); 
        return maintenanceRepository.deleteMaintenance(id);
    }

    public List<Maintenance> getTasksByFlower(long flowerId) {
        return maintenanceRepository.findByFlowerId(flowerId); 
    }

    public List<Maintenance> getTasksByType(MaintenanceType type) {
        return maintenanceRepository.findByMaintenanceType(type);
    }

    public List<Maintenance> getTasksByDate(LocalDateTime scheduledDate) {
        return maintenanceRepository.findByMaintenanceDate(scheduledDate);
    }
}
