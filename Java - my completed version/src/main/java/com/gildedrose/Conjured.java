package com.gildedrose;

public class Conjured extends Item {

    public Conjured(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void updateQuality() {
        sellIn = sellIn - 1;
        // a more verbose version
//        if (sellIn < 0) {
//            quality = Math.max(0, quality - 4);
//        } else {
//            quality = Math.max(0, quality - 2);
//        }

        // a cleaner version
        int decrease = (sellIn < 0) ? 4 : 2;
        quality = Math.max(0, quality - decrease);
    }
}
