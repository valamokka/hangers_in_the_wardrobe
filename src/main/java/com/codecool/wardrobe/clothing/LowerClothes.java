package com.codecool.wardrobe.clothing;

public class LowerClothes extends Clothes {


    public LowerClothes(String brandName, ClothesType type) {
        super(brandName);
        if (type.equals(ClothesType.SKIRT)||type.equals(ClothesType.TROUSERS)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Not supported clothes type.");
        }
    }
}
