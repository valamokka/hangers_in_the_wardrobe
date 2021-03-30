package com.codecool.wardrobe.clothing;


public class UpperClothes extends Clothes {


    public UpperClothes(String brandName, ClothesType type) {
        super(brandName);
        if (type.equals(ClothesType.BLOUSE)||type.equals(ClothesType.SHIRT)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Not supported clothes type.");
        }

    }
}
