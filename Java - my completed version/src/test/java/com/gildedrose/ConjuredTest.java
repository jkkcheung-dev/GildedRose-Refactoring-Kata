package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

public class ConjuredTest {

    private static final String CONJURED_NAME = "Conjured Mana Cake";

    @Test
    void shouldQualityDecreaseTwiceComparedToNormalItems_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(CONJURED_NAME, 1, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(CONJURED_NAME, 0, 18));
    }

    @Test
    void shouldQualityDecreaseFourTimesComparedToNormalItems_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(CONJURED_NAME, 0, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(CONJURED_NAME, -1, 16));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItStaysWithinSellIn() {
        Item[] items = new Item[] {
            new Item(CONJURED_NAME, 5, 0),
            new Item(CONJURED_NAME, 1, 1)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(CONJURED_NAME, 4, 0));
        assertItemEquals(app.items[1], new Item(CONJURED_NAME, 0, 0));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItPassesSellIn() {
        Item[] items = new Item[]{
            new Item(CONJURED_NAME, 0, 2),
            new Item(CONJURED_NAME, 0, 3),
            new Item(CONJURED_NAME, 0, 1)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(CONJURED_NAME, -1, 0));
        assertItemEquals(app.items[1], new Item(CONJURED_NAME, -1, 0));
        assertItemEquals(app.items[2], new Item(CONJURED_NAME, -1, 0));
    }

}
