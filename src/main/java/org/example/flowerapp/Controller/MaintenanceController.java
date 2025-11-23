package org.example.flowerapp.Controller;

import lombok.RequiredArgsConstructor;
import org.example.flowerapp.DTO.MaintenanceRequestDTO;
import org.example.flowerapp.DTO.MaintenanceResponseDTO;
import org.example.flowerapp.Models.Enums.MaintenanceType;
import org.example.flowerapp.Services.MaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> createNewMaintenance(@RequestBody MaintenanceRequestDTO dto) {
        MaintenanceResponseDTO created = maintenanceService.addNewMaintenance(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAllMaintenance() {
        List<MaintenanceResponseDTO> maintenances = maintenanceService.getAllMaintenance();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<MaintenanceResponseDTO> getMaintenanceById(@RequestBody long id) {
        MaintenanceResponseDTO maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping("/{flower_id}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByFlowerId(@RequestBody long id) {
        List<MaintenanceResponseDTO> maintenances = maintenanceService.getMaintenanceByFlowerId(id);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{maintenance_type}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByType(@RequestBody MaintenanceType maintenanceType) {
        List<MaintenanceResponseDTO> maintenances = maintenanceService.getMaintenanceByType(maintenanceType);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{maintenance_date}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByDate(@RequestBody LocalDateTime maintenanceDate) {
        List<MaintenanceResponseDTO> maintenances = maintenanceService.getMaintenanceByDate(maintenanceDate);
        return ResponseEntity.ok(maintenances);
    }

    @PutMapping("/{maintenance_id}")
    public ResponseEntity<MaintenanceResponseDTO> updateMaintenance(@RequestBody MaintenanceRequestDTO dto, long id) {
        MaintenanceResponseDTO updated = maintenanceService.updateMaintenance(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{maintenance_id}")
    public ResponseEntity<MaintenanceResponseDTO> deleteMaintenance(long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
