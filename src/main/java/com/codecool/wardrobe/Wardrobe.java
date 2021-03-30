package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;

import java.util.*;

public class Wardrobe {

    private final int LIMIT;
    private List<Hanger> hangers = new ArrayList<>();

    public Wardrobe(int limit) {
        if (limit <= 120) {
            this.LIMIT = limit;
        } else {
            throw new IllegalArgumentException("Maximum limit is 120.");
        }
    }

    public int getLimit() {
        return LIMIT;
    }

    public int count() {
        return hangers.size();
    }

    public void put(Hanger<? extends Clothes> hanger) {
        if (count() < LIMIT) {
            hangers.add(hanger);
        } else {
            throw new IllegalStateException("The wardrobe currently is full.");
        }
    }

    public Hanger<? extends Clothes> getHanger(Clothes.ClothesType clothesType) {
        return hangers.stream().filter(hanger -> hanger.hasSlotFor(clothesType)).findFirst().
                orElseThrow(NoSuchElementException::new);

    }

    public Clothes getClothes(UUID clothesId) {
        for (Hanger hanger : hangers) {
            if (hanger.takeOff(clothesId).isPresent()) {
                return (Clothes) hanger.takeOff(clothesId).get();
            }
        }
        throw new NoSuchElementException("Clothes not found.");
    }

    public static void main(String[] args) {

    }
}
