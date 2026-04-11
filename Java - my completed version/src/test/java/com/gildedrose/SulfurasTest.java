package com.gildedrose;

import org.junit.jupiter.api.Test;

import static com.gildedrose.ItemTestHelper.assertItemEquals;
public class SulfurasTest {

    private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";

    @Test
    void shouldNeverAlterAnyValue() {
        Item[] items = new Item[] { new Item(SULFURAS_NAME, 0, 80) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertItemEquals(app.items[0], new Item(SULFURAS_NAME, 0, 80));
    }
}
