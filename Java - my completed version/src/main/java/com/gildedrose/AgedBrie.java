package com.gildedrose;

public class AgedBrie extends Item {

    public AgedBrie(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void updateQuality() {
        sellIn = sellIn - 1;
        if (quality < 50) quality = quality + 1;
        if (sellIn < 0 && quality < 50) quality = quality + 1;
    }
}
