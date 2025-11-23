package org.example.flowerapp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowerapp.Models.Enums.GrowthStage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRequestDTO {
    private long flower_id;

    private GrowthStage stage;

    @NotBlank(message="Height is required!")
    private double height;

    private boolean colorChanges;
    private String notes;
}