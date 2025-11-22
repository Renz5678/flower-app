package org.example.flowerapp.Models.Enums;

public enum FlowerColor {
    RED("Red"),
    YELLOW("Yellow"),
    PINK("Pink"),
    WHITE("White"),
    PURPLE("Purple");

    private final String displayName;

    FlowerColor(String displayName) {
        this.displayName = displayName;
    }

    public String getColorName() {
        return this.displayName;
    }
}
