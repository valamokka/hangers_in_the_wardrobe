package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.UpperClothes;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.UUID;

import static com.codecool.wardrobe.clothing.Clothes.ClothesType;
import static com.codecool.wardrobe.clothing.Clothes.ClothesType.*;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class ShirtHangerTest {

    private final Hanger<UpperClothes> hanger;
    private final UpperClothes shirt;

    public ShirtHangerTest() {
        this.hanger = new ShirtHanger();
        shirt = new UpperClothes("Adidas", SHIRT);
    }

    @Test
    public void takeOff_emptyHanger_returnsEmpty() {
        Optional<UpperClothes> item = hanger.takeOff();
        boolean isPresent = item.isPresent();
        assertFalse(isPresent);
    }

    @Test
    public void put_upperClothes_canBeTakenOff() {
        hanger.put(shirt);
        Optional<UpperClothes> itemOnHanger = hanger.takeOff();

        assertEquals(shirt, itemOnHanger.get());
    }

    @Test
    public void put_alreadyOccupiedHanger_throwsIllegalStateException() {
        UpperClothes item2 = new UpperClothes("Adidas", BLOUSE);
        hanger.put(shirt);

        assertThrows(IllegalStateException.class, () -> hanger.put(item2));
    }

    @Test
    public void takeOff_byId_emptyHanger_returnsEmpty(){
        Optional<UpperClothes> item = hanger.takeOff(UUID.randomUUID());

        assertFalse(item.isPresent());
    }

    @Test
    public void takeOff_byId_notOnHanger_returnsEmpty(){
        hanger.put(shirt);
        Optional<UpperClothes> item = hanger.takeOff(UUID.randomUUID());

        assertFalse(item.isPresent());
    }

    @Test
    public void takeOff_byId_onHanger_returnsClothes(){
        hanger.put(shirt);
        UUID shirtId = shirt.getId();
        Optional<UpperClothes> result = hanger.takeOff(shirtId);

        assertEquals(shirt, result.get());
    }

    @Test
    @Parameters(method = "provideUpperTypes")
    public void hasSlotFor_emptyHangerForUpperClothes_returnsTrue(ClothesType type) {
        // using JUnitParams
        // https://www.baeldung.com/junit-params
        boolean answer = hanger.hasSlotFor(type);

        assertTrue(answer);
    }

    @Test
    @Parameters(method = "provideUpperTypes")
    public void hasSlotFor_occupiedHangerForUpperClothes_returnsFalse(ClothesType type) {
        hanger.put(shirt);

        boolean answer = hanger.hasSlotFor(type);

        assertFalse(answer);
    }

    @Test
    @Parameters(method = "provideLowerTypes")
    public void hasSlotFor_lowerClothesTypes_returnsFalse(ClothesType type) {
        boolean answer = hanger.hasSlotFor(type);

        assertFalse(answer);
    }

    private Object[] provideUpperTypes() {
        return new Object[]{SHIRT, BLOUSE};
    }

    private Object[] provideLowerTypes() {
        return new Object[]{TROUSERS, SKIRT};
    }
}
