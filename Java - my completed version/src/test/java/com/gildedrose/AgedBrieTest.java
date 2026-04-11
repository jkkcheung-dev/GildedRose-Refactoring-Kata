package com.gildedrose;

import org.junit.jupiter.api.Test;
import static com.gildedrose.ItemTestHelper.assertItemEquals;

public class AgedBrieTest {
    private static final String itemName = "Aged Brie";
    @Test
    void shouldQualityIncreaseOne_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(itemName, 1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, 0, 11));
    }

    @Test
    void shouldQualityIncreaseDouble_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(itemName, 0, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 12));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(itemName, 1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, 0, 1));
    }

    @Test
    void shouldQualityNeverBeNegative_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(itemName, 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 2));
    }

    @Test
    void shouldQualityNotOver50_whenItStaysWithinSellIn() {
        Item[] items = new Item[] { new Item(itemName, 1, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, 0, 50));
    }

    @Test
    void shouldQualityNotOver50_whenItPassesSellIn() {
        Item[] items = new Item[] { new Item(itemName, 0, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 50));
    }

    @Test
    void shouldQualityClampAt50_whenItPassesSellInWith49Quality() {
        Item[] items = new Item[] { new Item(itemName, 0, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals(app.items[0], new Item(itemName, -1, 50));
    }
}
