package org.example.flowerapp.Utils.Validators;

import org.example.flowerapp.Models.Maintenance;
import org.example.flowerapp.Models.Flower;

import java.time.LocalDateTime;

public class MaintenanceValidator {

    public static void validateMaintenanceTask(Maintenance maintenance) {
        if (maintenance == null) {
            throw new IllegalArgumentException("Maintenance task object cannot be null.");
        }
        
        Flower flower = maintenance.getFlower();
        if (flower == null || flower.getFlower_id() == 0) {
            throw new IllegalArgumentException("Maintenance task must be associated with an existing Flower (flower_id is required).");
        }

        if (maintenance.getTaskType() == null) {
            throw new IllegalArgumentException("Maintenance task type (WATER, FERTILIZE, etc.) is required.");
        }

        LocalDateTime scheduledDate = maintenance.getScheduledDate();
        if (scheduledDate == null) {
            throw new IllegalArgumentException("Scheduled date for the maintenance task is required.");
        }

        if (scheduledDate.isBefore(LocalDateTime.now().minusMinutes(1))) { 
            throw new IllegalArgumentException("Scheduled date cannot be in the past.");
        }

        String performedBy = maintenance.getPerformedBy();
        if (performedBy != null && performedBy.trim().length() < 3) {
             throw new IllegalArgumentException("If 'Performed By' is specified, it must be at least 3 characters long.");
        }
    }
}