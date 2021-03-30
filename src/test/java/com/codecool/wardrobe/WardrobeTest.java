package com.codecool.wardrobe;

import com.codecool.wardrobe.clothing.Clothes;
import com.codecool.wardrobe.clothing.LowerClothes;
import com.codecool.wardrobe.clothing.UpperClothes;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(JUnitParamsRunner.class)
public class WardrobeTest {

    private Wardrobe wardrobe;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        wardrobe = new Wardrobe(3);
    }

    @After
    public void restoreStreams() {
        // reset System.out.println
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void construct_withLimit_setLimit() {
        wardrobe = new Wardrobe(5);
        int size = wardrobe.getLimit();

        assertEquals(5, size);
    }

    @Test()
    public void construct_overAllowedLimit_throwIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> new Wardrobe(500));
        assertEquals("Maximum limit is 120.", thrown.getMessage());
    }

    @Test
    public void count_noHanger_returnsZero() {
        int amount = wardrobe.count();
        assertEquals(0, amount);
    }

    @Test
    public void put_emptyHangers_increaseCountAccordingly() {
        Hanger<UpperClothes> empty = new ShirtHanger();
        Hanger<Clothes> empty2 = new PantHanger();

        wardrobe.put(empty);
        wardrobe.put(empty2);

        assertEquals(2, wardrobe.count());
    }

    @Test
    public void put_hangersWithClothes_increaseCountAccordingly() {
        UpperClothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        LowerClothes pant = new LowerClothes("Adidas", Clothes.ClothesType.TROUSERS);
        Hanger<UpperClothes> hanger1 = new ShirtHanger();
        Hanger<Clothes> hanger2 = new PantHanger();

        hanger1.put(shirt);
        hanger2.put(pant);

        wardrobe.put(hanger1);
        wardrobe.put(hanger2);

        assertEquals(2, wardrobe.count());
    }

    @Test
    public void getHanger_shirt_returnsShirtHanger() {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        wardrobe.put(hanger);

        Hanger<?> received = wardrobe.getHanger(Clothes.ClothesType.SHIRT);

        assertEquals(hanger, received);
    }

    @Test
    public void getHanger_requestedShirtTypeHasOnlyFreePantHanger_returnsPantHanger() {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        UpperClothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        hanger.put(shirt);

        Hanger<Clothes> hanger2 = new PantHanger();
        wardrobe.put(hanger);
        wardrobe.put(hanger2);

        Hanger<?> received = wardrobe.getHanger(Clothes.ClothesType.SHIRT);

        assertEquals(hanger2, received);
    }

    @Test
    public void getHanger_noSuchHanger_throwsNoSuchElementException() {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        UpperClothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        hanger.put(shirt);

        wardrobe.put(hanger);

        assertThrows(NoSuchElementException.class, () -> wardrobe.getHanger(Clothes.ClothesType.SHIRT));
    }

    @Test
    public void getClothes_notInWardrobe_throwsNoSuchElementException() {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        Clothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        UUID clothesId = shirt.getId();

        wardrobe.put(hanger);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> wardrobe.getClothes(clothesId));
        assertEquals("Clothes not found.", thrown.getMessage());
    }

    @Test
    @Parameters(method = "provideUpperClothes")
    public void getClothes_upperClothesInWardrobeOnShirtHanger_returnsWithUpperClothes(UpperClothes clothes, UUID id) {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        hanger.put(clothes);
        wardrobe.put(hanger);

        Clothes receivedClothes = wardrobe.getClothes(id);

        assertEquals(clothes, receivedClothes);
    }

    @Test
    @Parameters(method = "provideLowerClothes")
    public void getClothes_lowerClothesInWardrobeOnPantHanger_returnsWithLowerClothes(Clothes clothes, UUID id) {
        Hanger<Clothes> hanger = new PantHanger();
        hanger.put(clothes);
        wardrobe.put(hanger);

        Clothes receivedClothes = wardrobe.getClothes(id);

        assertEquals(clothes, receivedClothes);
    }

    @Test
    public void getClothes_multipleHangersAndClothes_returnsClothesById() {
        Hanger<UpperClothes> hanger = new ShirtHanger();
        Hanger<Clothes> hanger2 = new PantHanger();
        Hanger<UpperClothes> hanger3 = new ShirtHanger();
        LowerClothes pant = new LowerClothes("Adidas", Clothes.ClothesType.TROUSERS);
        UUID pantID = pant.getId();
        UpperClothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        UpperClothes shirt2 = new UpperClothes("Budmil", Clothes.ClothesType.SHIRT);
        hanger.put(shirt);
        hanger2.put(pant);
        hanger3.put(shirt2);
        wardrobe.put(hanger);
        wardrobe.put(hanger2);
        wardrobe.put(hanger3);

        Clothes receivedPant = wardrobe.getClothes(pantID);

        assertEquals(pant, receivedPant);
    }

    private Object[] provideUpperClothes() {
        Clothes shirt = new UpperClothes("Adidas", Clothes.ClothesType.SHIRT);
        Clothes blouse = new UpperClothes("Louis Vuitton", Clothes.ClothesType.BLOUSE);

        return new Object[]{
                new Object[]{shirt, shirt.getId()},
                new Object[]{blouse, blouse.getId()}
        };
    }

    private Object[] provideLowerClothes() {
        Clothes trousers = new LowerClothes("Adidas", Clothes.ClothesType.TROUSERS);
        Clothes skirt = new LowerClothes("Louis Vuitton", Clothes.ClothesType.SKIRT);

        return new Object[]{
                new Object[]{trousers, trousers.getId()},
                new Object[]{skirt, skirt.getId()}
        };
    }
}
