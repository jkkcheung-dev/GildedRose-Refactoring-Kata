package com.gildedrose;

import org.junit.jupiter.api.Test;

// import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.gildedrose.ItemTestHelper.assertItemEquals;
class GildedRoseTest {
    private static final String itemName = "other";

    @Test
    void shouldQualityDecreaseOne_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(itemName, 10, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, 9, 49));
    }

    @Test
    void shouldQualityDecreaseTwo_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(itemName, 0, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 48));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(itemName, 1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, 0, 0));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(itemName, 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 0));
    }

    @Test
    void shouldQualityClampAtZero_whenItPassesSellInWithQualityOne() {
        Item[] items = new Item[] { new Item(itemName, 0, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 0));
    }
}
