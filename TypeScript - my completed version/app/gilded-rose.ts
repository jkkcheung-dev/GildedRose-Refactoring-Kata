import { AGED_BRIE, BACKSTAGE_PASSES, CONJURED, SULFURAS, type ItemUpdater } from '#app/item-updater';
import { AgedBrieUpdater } from '#app/updaters/aged-brie-updater';
import { BackstagePassUpdater } from '#app/updaters/backstage-pass-updater';
import { ConjuredItemUpdater } from '#app/updaters/conjured-item-updater';
import { NormalItemUpdater } from '#app/updaters/normal-item-updater';
import { SulfurasUpdater } from '#app/updaters/sulfuras-updater';

export class Item {
  name: string;
  sellIn: number;
  quality: number;

  constructor(name, sellIn, quality) {
    this.name = name;
    this.sellIn = sellIn;
    this.quality = quality;
  }
}

export class GildedRose {
  items: Array<Item>;
  private readonly normalUpdater = new NormalItemUpdater();
  private readonly agedBrieUpdater = new AgedBrieUpdater();
  private readonly backstagePassUpdater = new BackstagePassUpdater();
  private readonly conjuredItemUpdater = new ConjuredItemUpdater();
  private readonly sulfurasUpdater = new SulfurasUpdater();

  constructor(items = [] as Array<Item>) {
    this.items = items;
  }

  private getUpdater(item: Item): ItemUpdater {
    if (item.name.startsWith(CONJURED)) {
      return this.conjuredItemUpdater;
    }

    switch (item.name) {
      case AGED_BRIE:
        return this.agedBrieUpdater;
      case BACKSTAGE_PASSES:
        return this.backstagePassUpdater;
      case SULFURAS:
        return this.sulfurasUpdater;
      default:
        return this.normalUpdater;
    }
  }

  updateQuality() {
    for (const item of this.items) {
      this.getUpdater(item).update(item);
    }

    return this.items;
  }
}
