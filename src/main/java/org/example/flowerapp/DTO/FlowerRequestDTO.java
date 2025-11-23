package org.example.flowerapp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.flowerapp.Models.Enums.FlowerColor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowerRequestDTO {

    @NotBlank(message="Flower name is required")
    private String flowerName;

    private String species;
    private FlowerColor color;
    private LocalDateTime plantingDate;
}