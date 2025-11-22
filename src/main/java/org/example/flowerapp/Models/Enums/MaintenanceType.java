package org.example.flowerapp.Models.Enums;

import org.example.flowerapp.Models.Maintenance;

public enum MaintenanceType {
    WATER("Water"),
    FERTILIZE("Fertilize"),
    REPOT("Repot"),
    PEST_CHECK("Check for pests"),
    PRUNING("Prune branches");

    private final String maintenanceType;

    MaintenanceType(String maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    public String getMaintenanceType() {
        return this.maintenanceType;
    }
}
