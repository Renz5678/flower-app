package org.example.flowerapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowerapp.Models.Enums.MaintenanceType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDTO {
    private long task_id;
    private long flower_id;
    private MaintenanceType maintenanceType;
    private LocalDateTime maintenanceDate;
    private String notes;
    private String performedBy;
    private LocalDateTime createdAt;
}