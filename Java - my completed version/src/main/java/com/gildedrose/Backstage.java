package com.gildedrose;

public class Backstage extends Item {

    public Backstage(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void updateQuality() {
        sellIn = sellIn - 1;
        if (sellIn < 0) {
            quality = 0;
        } else if (quality < 50) {
            quality = quality + 1;

            if (sellIn < 10 && quality < 50) quality = quality + 1;

            if (sellIn < 5 & quality < 50) quality = quality + 1;
        }
    }
}
