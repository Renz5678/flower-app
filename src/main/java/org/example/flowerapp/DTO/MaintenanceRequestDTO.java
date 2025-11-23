package org.example.flowerapp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowerapp.Models.Enums.MaintenanceType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDTO {
    private long flower_id;

    @NotBlank(message="Maintenance Type is required!")
    private MaintenanceType maintenanceType;

    private LocalDateTime maintenanceDate;
    private String notes;
    private String performedBy;
}