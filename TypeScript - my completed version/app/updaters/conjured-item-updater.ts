import type { ItemUpdater } from '#app/item-updater';
import type { Item } from '#app/gilded-rose';

export class ConjuredItemUpdater implements ItemUpdater {
  update(item: Item) {
    if (item.quality > 0) {
      item.quality = item.quality - 2;
    }

    if (item.quality < 0) {
      item.quality = 0;
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0 && item.quality > 0) {
      item.quality = item.quality - 2;
    }

    if (item.quality < 0) {
      item.quality = 0;
    }
  }
}
