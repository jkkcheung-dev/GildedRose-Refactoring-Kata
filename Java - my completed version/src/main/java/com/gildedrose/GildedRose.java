package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = ItemFactory.createItem(items[i].name, items[i].sellIn, items[i].quality);
            ItemUpdater.update(item);
            items[i] = item;
        }
    }
}
