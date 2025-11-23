package org.example.flowerapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowerapp.Models.Enums.FlowerColor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowerResponseDTO {
    private long flower_id;
    private String flowerName;
    private String species;
    private FlowerColor color;
    private LocalDateTime plantingDate;
}