package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;

import java.util.Optional;
import java.util.UUID;

public interface Hanger<T extends Clothes> {
    Optional<T> takeOff();

    Optional<T> takeOff(UUID id);
    
    void put(T item);

    boolean hasSlotFor(Clothes.ClothesType type);
}
