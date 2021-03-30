package com.codecool.wardrobe.clothing;

import java.util.UUID;

public class Clothes {

    private final UUID ID;
    private final String BRAND_NAME;
    protected ClothesType type;

    public Clothes(String brandName) {
        this.BRAND_NAME = brandName;
        this.ID = UUID.randomUUID();
    }

    public UUID getId() {
        return ID;
    }

    public String getBrandName() {
        return BRAND_NAME;
    }

    public ClothesType getType() {
        return this.type;
    }

    public enum ClothesType {
        SHIRT,
        BLOUSE,
        TROUSERS,
        SKIRT
    }
}
