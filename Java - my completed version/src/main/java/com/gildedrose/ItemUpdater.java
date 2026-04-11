package com.gildedrose;

public final class ItemUpdater {

    private ItemUpdater() {
    }

    public static void update(Item item) {
        if (item instanceof AgedBrie) {
            ((AgedBrie) item).updateQuality();
        } else if (item instanceof Backstage) {
            ((Backstage) item).updateQuality();
        } else if (item instanceof Sulfuras) {
            ((Sulfuras) item).updateQuality();
        } else if (item instanceof Conjured) {
            ((Conjured) item).updateQuality();
        } else if (item instanceof Normal) {
            ((Normal) item).updateQuality();
        }
    }
}
