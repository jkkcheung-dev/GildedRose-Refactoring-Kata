package com.gildedrose;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class ItemTestHelper {
    static void assertItemEquals(Item actual, Item expected) {
        assertEquals(expected.name, actual.name);
        assertEquals(expected.sellIn, actual.sellIn);
        assertEquals(expected.quality, actual.quality);
    }
}
