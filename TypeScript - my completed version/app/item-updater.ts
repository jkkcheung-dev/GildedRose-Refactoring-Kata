import type { Item } from '#app/gilded-rose';

export const AGED_BRIE = 'Aged Brie';
export const BACKSTAGE_PASSES = 'Backstage passes to a TAFKAL80ETC concert';
export const CONJURED = 'Conjured';
export const SULFURAS = 'Sulfuras, Hand of Ragnaros';

export interface ItemUpdater {
  update(item: Item): void;
}
