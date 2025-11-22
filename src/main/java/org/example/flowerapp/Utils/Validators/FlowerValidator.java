package org.example.flowerapp.Utils.Validators;

import org.example.flowerapp.Models.Enums.FlowerColor;
import org.example.flowerapp.Models.Flower;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlowerValidator {

    public static void validateFlower(Flower flower) {
        if (flower == null) {
            throw new IllegalArgumentException("Flower data cannot be null.");
        }

        if (flower.getFlowerName() == null || flower.getFlowerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Flower name is required.");
        }
        if (flower.getSpecies() == null || flower.getSpecies().trim().isEmpty()) {
            throw new IllegalArgumentException("Flower species is required.");
        }
        if (flower.getColor() == null) {
            throw new IllegalArgumentException("Flower color is required.");
        }

        Date plantingDate = flower.getPlantingDate();
        if (plantingDate == null) {
            throw new IllegalArgumentException("Planting date cannot be null.");
        }

        if (plantingDate.after(new Date())) {
            throw new IllegalArgumentException("Planting date cannot be in the future.");
        }
    }

    public static FlowerColor validateColorEnum(String colorString) {
        if (colorString == null || colorString.trim().isEmpty()) {
            throw new IllegalArgumentException("Color string cannot be empty.");
        }
        try {
            return FlowerColor.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validColors = java.util.Arrays.stream(FlowerColor.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            
            throw new IllegalArgumentException(
                "Invalid flower color: '" + colorString + "'. Valid colors are: " + validColors
            );
        }
    }

    public static void checkDuplicate(Flower newFlower, List<Flower> existingFlowers, boolean isUpdate) {
        for (Flower f : existingFlowers) {
            if (isUpdate && f.getFlower_id() == newFlower.getFlower_id()) {
                continue;
            }

            boolean sameName = f.getFlowerName().equalsIgnoreCase(newFlower.getFlowerName());
            boolean sameSpecies = f.getSpecies().equalsIgnoreCase(newFlower.getSpecies());
            
            boolean sameDate = f.getPlantingDate().equals(newFlower.getPlantingDate());

            if (sameName && sameSpecies && sameDate) {
                throw new IllegalArgumentException("Duplicate flower detected: Same name, species, and planting date already exist.");
            }
        }
    }
}