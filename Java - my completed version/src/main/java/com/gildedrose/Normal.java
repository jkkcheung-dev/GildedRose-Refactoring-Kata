package com.gildedrose;

public class Normal extends Item {

    public Normal(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void updateQuality() {
        sellIn = sellIn - 1;
//        if (quality > 0) quality = quality - 1;
//        if (sellIn < 0 && quality > 0) quality = quality - 1;
        int decrease = (sellIn < 0) ? 2 : 1;
        quality = Math.max(0,quality - decrease);
    }
}
