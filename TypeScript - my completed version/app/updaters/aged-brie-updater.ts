import type { ItemUpdater } from '#app/item-updater';
import type { Item } from '#app/gilded-rose';

export class AgedBrieUpdater implements ItemUpdater {
  update(item: Item) {
    if (item.quality < 50) {
      item.quality = item.quality + 1;
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0 && item.quality < 50) {
      item.quality = item.quality + 1;
    }
  }
}
