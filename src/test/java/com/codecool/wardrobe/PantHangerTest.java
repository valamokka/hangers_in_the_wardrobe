package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;
import com.codecool.wardrobe.clothing.LowerClothes;
import com.codecool.wardrobe.clothing.UpperClothes;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static com.codecool.wardrobe.clothing.Clothes.ClothesType.*;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class PantHangerTest {

    private final Hanger<Clothes> hanger;
    private final Clothes upperClothes;
    private final Clothes lowerClothes;

    public PantHangerTest() {
        hanger = new PantHanger();
        upperClothes = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        lowerClothes = new LowerClothes("Adidas", Clothes.ClothesType.TROUSERS);
    }

    @Test
    public void takeOff_emptyHanger_returnsEmpty() {
        Optional<Clothes> item = hanger.takeOff();

        assertFalse(item.isPresent());
    }

    @Test
    public void put_upperClothes_storedOnHanger() {
        hanger.put(upperClothes);
        Optional<Clothes> itemOnHanger = hanger.takeOff();

        assertEquals(upperClothes, itemOnHanger.get());
    }

    @Test
    public void put_upperClothesAlreadyOccupiedHanger_throwsIllegalStateException() {
        Clothes item2 = new UpperClothes("Adidas", Clothes.ClothesType.BLOUSE);
        hanger.put(upperClothes);

        assertThrows(IllegalStateException.class, () -> hanger.put(item2));
    }

    @Test
    public void put_upperAndLowerClothesAsWell_storedClothes() {
        List<Clothes> clothes = Arrays.asList(lowerClothes, upperClothes);

        hanger.put(clothes.get(0));
        hanger.put(clothes.get(1));

        Optional<Clothes> receivedClothes1 = hanger.takeOff();
        Optional<Clothes> receivedClothes2 = hanger.takeOff();
        Optional<Clothes> emptyItem = hanger.takeOff();

        assertNotEquals(receivedClothes1.get(), receivedClothes2.get());
        assertTrue(clothes.contains(receivedClothes1.get()));
        assertTrue(clothes.contains(receivedClothes2.get()));
        assertFalse(emptyItem.isPresent());
    }

    @Test
    public void put_lowerClothesAlreadyOccupied_throwsIllegalStateException() {
        Clothes item2 = new LowerClothes("Prada", Clothes.ClothesType.SKIRT);
        hanger.put(lowerClothes);

        assertThrows(IllegalStateException.class, () -> hanger.put(item2));
    }

    @Test
    public void takeOff_onlyUpperClothes_returnsUpperClothes() {
        hanger.put(upperClothes);

        Optional<Clothes> requestedClothes = hanger.takeOff();

        assertEquals(upperClothes, requestedClothes.get());
    }

    @Test
    public void takeOff_onlyLowerClothes_returnsLowerClothes() {
        hanger.put(lowerClothes);

        Optional<Clothes> requestedClothes = hanger.takeOff();

        assertEquals(lowerClothes, requestedClothes.get());
    }

    @Test
    public void takeOff_fullyOccupiedHanger_returnsFirstUpperSecondLower() {
        hanger.put(upperClothes);
        hanger.put(lowerClothes);

        UpperClothes firstReceived = (UpperClothes) hanger.takeOff().get();
        LowerClothes secondReceived = (LowerClothes) hanger.takeOff().get();

        assertEquals(upperClothes, firstReceived);
        assertEquals(lowerClothes, secondReceived);
    }

    @Test
    public void takeOff_byId_emptyHanger_returnsEmpty() {
        Optional<Clothes> clothes = hanger.takeOff(UUID.randomUUID());

        assertFalse(clothes.isPresent());
    }

    @Test
    public void takeOff_byId_notOnHanger_returnsEmpty() {
        hanger.put(upperClothes);
        Optional<Clothes> clothes = hanger.takeOff(UUID.randomUUID());

        assertFalse(clothes.isPresent());
    }

    @Test
    public void takeOff_byId_upperClothesOnHanger_returnsWithClothes() {
        hanger.put(upperClothes);
        UUID upperClothesId = upperClothes.getId();

        Optional<Clothes> receivedClothes = hanger.takeOff(upperClothesId);

        assertEquals(upperClothes, receivedClothes.get());
    }

    @Test
    public void takeOff_byId_lowerClothesOnHanger_returnsWithClothes() {
        hanger.put(lowerClothes);
        UUID upperClothesId = lowerClothes.getId();

        Optional<Clothes> receivedClothes = hanger.takeOff(upperClothesId);

        assertEquals(lowerClothes, receivedClothes.get());
    }

    @Test
    @Parameters(method = "provideUpperClothes")
    public void hasSlotFor_emptyHangerForUpperClothes_returnsTrue(Clothes.ClothesType type) {
        // using JUnitParams
        // https://www.baeldung.com/junit-params
        boolean answer = hanger.hasSlotFor(type);

        assertTrue(answer);
    }

    @Test
    @Parameters(method = "provideUpperClothes")
    public void hasSlotFor_occupiedHangerForUpperClothes_returnsFalse(Clothes.ClothesType type) {
        hanger.put(upperClothes);

        boolean answer = hanger.hasSlotFor(type);

        assertFalse(answer);
    }

    @Test
    @Parameters(method = "provideLowerClothes")
    public void hasSlotFor_occupiedHangerForLowerClothes_returnsFalse(Clothes.ClothesType type) {
        hanger.put(lowerClothes);

        boolean answer = hanger.hasSlotFor(type);

        assertFalse(answer);
    }

    @Test
    @Parameters(method = "provideLowerClothes")
    public void hasSlotFor_semiOccupiedHangerForLowerClothes_returnsTrue(Clothes.ClothesType type) {
        hanger.put(upperClothes);

        boolean answer = hanger.hasSlotFor(type);

        assertTrue(answer);
    }

    @Test
    @Parameters(method = "provideLowerClothes")
    public void hasSlotFor_lowerClothesTypes_returnsTrue(Clothes.ClothesType type) {
        boolean answer = hanger.hasSlotFor(type);

        assertTrue(answer);
    }

    private Object[] provideUpperClothes() {
        return new Object[]{SHIRT, BLOUSE};
    }

    private Object[] provideLowerClothes() {
        return new Object[]{TROUSERS, SKIRT};
    }
}
