package org.example.flowerapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrowthResponseDTO {
    private long growth_id;
    private long flower_id;
    private String stage;
    private double height;
    private boolean colorChanges;
    private String notes;
}