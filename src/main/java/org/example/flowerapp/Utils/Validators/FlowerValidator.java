package org.example.flowerapp.Utils.Validators;

import org.example.flowerapp.Models.Enums.FlowerColor;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidFlowerDataException;
import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidFlowerDateException;
import org.example.flowerapp.Models.Flower;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class FlowerValidator {

    public static void validateFlower(Flower flower) {
        if (flower == null) {
            throw new InvalidFlowerDataException("Flower data cannot be null.");
        }

        if (flower.getFlowerName() == null || flower.getFlowerName().trim().isEmpty()) {
            throw new InvalidFlowerDataException("Flower name is required.");
        }
        if (flower.getSpecies() == null || flower.getSpecies().trim().isEmpty()) {
            throw new InvalidFlowerDataException("Flower species is required.");
        }
        if (flower.getColor() == null) {
            throw new InvalidFlowerDataException("Flower color is required.");
        }

        LocalDateTime plantingDate = flower.getPlantingDate();
        if (plantingDate == null) {
            throw new InvalidFlowerDataException("Planting date cannot be null.");
        }

        if (plantingDate.isAfter(LocalDateTime.now())) {
            throw new InvalidFlowerDateException("Planting date cannot be in the future.");
        }
    }

    public static FlowerColor validateColorEnum(String colorString) {
        if (colorString == null || colorString.trim().isEmpty()) {
            throw new InvalidFlowerDataException("Color string cannot be empty.");
        }
        try {
            return FlowerColor.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validColors = java.util.Arrays.stream(FlowerColor.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            
            throw new InvalidFlowerDataException("Invalid flower color: '" + colorString + "'. Valid colors are: " + validColors);
        }
    }
}