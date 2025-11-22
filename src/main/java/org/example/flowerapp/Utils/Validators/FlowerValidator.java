package org.example.flowerapp.Utils.Validators;

import org.example.flowerapp.Models.Enums.FlowerColor;
import org.example.flowerapp.Exceptions.InvalidDateException;
import org.example.flowerapp.Exceptions.InvalidInputException;
import org.example.flowerapp.Models.Flower;
import java.util.Date;
import java.util.stream.Collectors;

public class FlowerValidator {

    public static void validateFlower(Flower flower) {
        if (flower == null) {
            throw new InvalidInputException("Flower data cannot be null.");
        }

        if (flower.getFlowerName() == null || flower.getFlowerName().trim().isEmpty()) {
            throw new InvalidInputException("Flower name is required.");
        }
        if (flower.getSpecies() == null || flower.getSpecies().trim().isEmpty()) {
            throw new InvalidInputException("Flower species is required.");
        }
        if (flower.getColor() == null) {
            throw new InvalidInputException("Flower color is required.");
        }

        Date plantingDate = flower.getPlantingDate();
        if (plantingDate == null) {
            throw new InvalidDateException("Planting date cannot be null.");
        }

        if (plantingDate.after(new Date())) {
            throw new InvalidDateException("Planting date cannot be in the future.");
        }
    }

    public static FlowerColor validateColorEnum(String colorString) {
        if (colorString == null || colorString.trim().isEmpty()) {
            throw new InvalidInputException("Color string cannot be empty.");
        }
        try {
            return FlowerColor.valueOf(colorString.toUpperCase());
        } catch (IllegalArgumentException e) {
            String validColors = java.util.Arrays.stream(FlowerColor.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            
            throw new InvalidInputException("Invalid flower color: '" + colorString + "'. Valid colors are: " + validColors);
        }
    }
}