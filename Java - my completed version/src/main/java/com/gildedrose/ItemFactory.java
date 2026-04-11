package com.gildedrose;

public final class ItemFactory {

    private ItemFactory() {
    }

    public static Item createItem(String name, int sellIn, int quality) {
        return switch (name) {
            case "Aged Brie" -> new AgedBrie(name, sellIn, quality);
            case "Backstage passes to a TAFKAL80ETC concert" -> new Backstage(name, sellIn, quality);
            case "Sulfuras, Hand of Ragnaros" -> new Sulfuras(name, sellIn, quality);
            case "Conjured Mana Cake" -> new Conjured(name, sellIn, quality);
            default -> new Normal(name, sellIn, quality);
        };
    }


    // below is a modern approach
//    @FunctionalInterface
//    interface ItemCreator {
//        Item create(String name, int sellIn, int quality);
//    }
//
//    private static final Map<String, ItemCreator> ITEM_CREATORS = Map.of(
//        "Aged Brie", AgedBrie::new,
//        "Backstage passes to a TAFKAL80ETC concert", Backstage::new,
//        "Sulfuras, Hand of Ragnaros", Sulfuras::new,
//        "Conjured Mana Cake", Conjured::new
//    );
//
//    public static Item createItem(String name, int sellIn, int quality) {
//        return ITEM_CREATORS
//            .getOrDefault(name, Normal::new)
//            .create(name, sellIn, quality);
//    }
}
