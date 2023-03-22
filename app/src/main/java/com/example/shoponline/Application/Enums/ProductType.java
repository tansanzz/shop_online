package com.example.shoponline.Application.Enums;

public enum ProductType {
    // Food
    FOOD(0),

    // Drink
    DRINK(1);

    private final int value;

    ProductType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
