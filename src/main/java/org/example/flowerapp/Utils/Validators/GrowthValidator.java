package org.example.flowerapp.Utils.Validators;

import org.example.flowerapp.Exceptions.ValidationExceptions.InvalidGrowthDataException;
import org.example.flowerapp.Models.Flower;
import org.example.flowerapp.Models.Growth;

public class GrowthValidator {

    public static void validateGrowth(Growth growth) {
        if (growth == null) {
            throw new InvalidGrowthDataException("Growth record object cannot be null.");
        }
        
        Flower flower = growth.getFlower();
        if (flower == null || flower.getFlower_id() == 0) {
            throw new InvalidGrowthDataException("Growth record must be associated with an existing Flower (flower_id is required).");
        }

        if (growth.getStage() == null) {
            throw new InvalidGrowthDataException("Growth stage is required.");
        }

        if (growth.getHeight() < 0) {
            throw new InvalidGrowthDataException("Height measurement cannot be negative.");
        }
        
        String notes = growth.getNotes();
        if (notes != null && notes.trim().length() > 500) {
            throw new InvalidGrowthDataException("Notes field exceeds the maximum length (500 characters).");
        }
    }
}