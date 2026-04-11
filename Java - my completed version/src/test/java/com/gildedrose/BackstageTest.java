package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;

public class BackstageTest {

    private static final String BACKSTAGE_NAME = "Backstage passes to a TAFKAL80ETC concert";

    @Test
    void shouldQualityIncreaseOne_whenSellInMoreThan10() {
        Item[] items = new Item[] { new Item(BACKSTAGE_NAME, 11, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE_NAME, 10, 21));
    }

    @Test
    void shouldQualityIncreaseTwo_whenSellInBetween10And6() {
        Item[] items = new Item[] {
            new Item(BACKSTAGE_NAME, 10, 20),
            new Item(BACKSTAGE_NAME, 6, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE_NAME, 9, 22));
        assertItemEquals(app.items[1], new Item(BACKSTAGE_NAME, 5, 22));
    }

    @Test
    void shouldQualityIncreaseThree_whenSellInLessThan6() {
        Item[] items = new Item[] {
            new Item(BACKSTAGE_NAME, 5, 20),
            new Item(BACKSTAGE_NAME, 1, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE_NAME, 4, 23));
        assertItemEquals(app.items[1], new Item(BACKSTAGE_NAME, 0, 23));
    }

    @Test
    void shouldQualityDropToZero_whenSellInLessThanZero() {
        Item[] items = new Item[] {
            new Item(BACKSTAGE_NAME, 0, 20),
            new Item(BACKSTAGE_NAME, -2, 20)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE_NAME, -1, 0));
        assertItemEquals(app.items[1], new Item(BACKSTAGE_NAME, -3, 0));
    }

    @Test
    void shouldQualityClampAt50_whenSellInIs10AndQualityIs49() {
        Item[] items = new Item[] {
            new Item(BACKSTAGE_NAME, 11, 49),
            new Item(BACKSTAGE_NAME, 10, 49),
            new Item(BACKSTAGE_NAME, 5, 49)
        };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(BACKSTAGE_NAME, 10, 50));
        assertItemEquals(app.items[1], new Item(BACKSTAGE_NAME, 9, 50));
        assertItemEquals(app.items[2], new Item(BACKSTAGE_NAME, 4, 50));
    }
}
