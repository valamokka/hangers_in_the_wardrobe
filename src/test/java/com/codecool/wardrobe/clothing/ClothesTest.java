package com.codecool.wardrobe.clothing;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class ClothesTest {
    @Test
    public void constructor_createsId() {
        Clothes tShirt = new Clothes("");
        Clothes blouse = new Clothes("");
        UUID id = tShirt.getId();
        assertNotNull(id);
        assertNotEquals(blouse.getId(), tShirt.getId());
    }

    @Test
    public void constructor_withBrandName_setBrandName() {
        Clothes tShirt = new Clothes("Adidas");
        assertEquals("Adidas", tShirt.getBrandName());
    }

    @Test
    public void upperClothes_typeShirt_setTypeAccordingly() {
        Clothes tShirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        assertEquals(Clothes.ClothesType.SHIRT, tShirt.getType());
    }

    @Test
    public void upperClothes_typeBlouse_setTypeAccordingly() {
        Clothes tShirt = new UpperClothes("Adidas", Clothes.ClothesType.BLOUSE);
        assertEquals(Clothes.ClothesType.BLOUSE, tShirt.getType());
    }

    @Test
    public void upperClothes_lowerType_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new UpperClothes("Adidas", Clothes.ClothesType.TROUSERS));
        assertEquals("Not supported clothes type.", thrown.getMessage());
    }

    @Test
    public void lowerClothes_typeSkirt_setTypeAccordingly() {
        Clothes tShirt = new LowerClothes("Adidas", Clothes.ClothesType.SKIRT);
        assertEquals(Clothes.ClothesType.SKIRT, tShirt.getType());
    }

    @Test
    public void lowerClothes_typeTrousers_setTypeAccordingly() {
        Clothes tShirt = new LowerClothes("Adidas", Clothes.ClothesType.TROUSERS);
        assertEquals(Clothes.ClothesType.TROUSERS, tShirt.getType());
    }

}