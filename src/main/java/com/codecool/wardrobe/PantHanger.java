package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;
import com.codecool.wardrobe.clothing.Clothes.ClothesType;
import com.codecool.wardrobe.clothing.LowerClothes;
import com.codecool.wardrobe.clothing.UpperClothes;

import java.util.*;

public class PantHanger implements Hanger<Clothes> {

    private UpperClothes upper;
    private LowerClothes lower;


    @Override
    public Optional<Clothes> takeOff() {
        if (upper != null) {
            Optional<Clothes> up = Optional.of(upper);
            upper = null;
            return up;
        } else if (lower != null) {
            Optional<Clothes> low = Optional.of(lower);
            lower = null;
            return low;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Clothes> takeOff(UUID id) {
        if (upper != null) {
            if (upper.getId() == id) return Optional.of(upper);
        } else if (lower != null) {
            if (lower.getId() == id) return Optional.of(lower);
        }
        return Optional.empty();
    }

    @Override
    public void put(Clothes item) {

        if (hasSlotFor(item.getType()) && item instanceof UpperClothes) {
            this.upper = (UpperClothes) item;
        } else if (hasSlotFor(item.getType()) && item instanceof LowerClothes) {
            this.lower = (LowerClothes) item;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean hasSlotFor(ClothesType type) {
        switch (type) {
            case SHIRT:
            case BLOUSE:
                return upper == null;
            case TROUSERS:
            case SKIRT:
                return lower == null;
        }
        return false;
    }
}
