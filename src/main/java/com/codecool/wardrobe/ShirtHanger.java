package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;
import com.codecool.wardrobe.clothing.UpperClothes;

import java.util.Optional;
import java.util.UUID;

public class ShirtHanger implements Hanger<UpperClothes> {

    private UpperClothes upper;

    @Override
    public Optional<UpperClothes> takeOff() {
        return upper == null ? Optional.empty() : Optional.of(upper);
    }

    @Override
    public Optional<UpperClothes> takeOff(UUID id) {
        return upper != null && upper.getId() == id ? Optional.of(upper) : Optional.empty();
    }

    @Override
    public void put(UpperClothes item) {
        if (hasSlotFor(item.getType())) {
            this.upper = item;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean hasSlotFor(Clothes.ClothesType type) {
        if (type == Clothes.ClothesType.SKIRT || type == Clothes.ClothesType.TROUSERS) {
            return false;
        }
        return upper == null;
    }
}
