import { Item, GildedRose } from '#app/gilded-rose';

describe('Gilded Rose', () => {
  describe('basic behavior', () => {
    it('should foo', () => {
      const gildedRose = new GildedRose([new Item('foo', 0, 0)]);
      const items = gildedRose.updateQuality();
      expect(items[0].name).toBe('foo');
    });

    it('still routes unknown item names through normal-item behavior', () => {
      const gildedRose = new GildedRose([new Item('foo', 0, 2)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(0);
    });
  });

  describe('normal items', () => {
    it('decreases sellIn by 1 and quality by 1 for a normal item before the sell date', () => {
      const gildedRose = new GildedRose([new Item('normal item', 10, 20)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(9);
      expect(item.quality).toBe(19);
    });

    it('decreases sellIn by 1 and quality by 2 for a normal item after the sell date', () => {
      const gildedRose = new GildedRose([new Item('normal item', 0, 20)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(18);
    });

    it('never decreases the quality of a normal item below 0', () => {
      const gildedRose = new GildedRose([new Item('normal item', 0, 0)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(0);
    });
  });

  describe('Conjured items', () => {
    it('decreases sellIn by 1 and quality by 2 before the sell date', () => {
      const gildedRose = new GildedRose([new Item('Conjured Mana Cake', 3, 6)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(2);
      expect(item.quality).toBe(4);
    });

    it('decreases sellIn by 1 and quality by 4 after the sell date', () => {
      const gildedRose = new GildedRose([new Item('Conjured Mana Cake', 0, 6)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(2);
    });

    it('never decreases quality below 0', () => {
      const gildedRose = new GildedRose([new Item('Conjured Mana Cake', 0, 3)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(0);
    });
  });

  describe('Sulfuras', () => {
    it('does not change sellIn or quality for Sulfuras after updater extraction', () => {
      const gildedRose = new GildedRose([new Item('Sulfuras, Hand of Ragnaros', 0, 80)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(0);
      expect(item.quality).toBe(80);
    });
  });

  describe('Aged Brie', () => {
    it('increases quality by 1 for Aged Brie before the sell date', () => {
      const gildedRose = new GildedRose([new Item('Aged Brie', 10, 20)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(9);
      expect(item.quality).toBe(21);
    });

    it('increases quality by 2 for Aged Brie after the sell date', () => {
      const gildedRose = new GildedRose([new Item('Aged Brie', 0, 20)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(22);
    });

    it('caps Aged Brie quality at 50 even after the sell date', () => {
      const gildedRose = new GildedRose([new Item('Aged Brie', 0, 49)]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(50);
    });

  });

  describe('Backstage passes', () => {
    it('increases Backstage passes quality by 1 when sellIn is greater than 10', () => {
      const gildedRose = new GildedRose([
        new Item('Backstage passes to a TAFKAL80ETC concert', 11, 20),
      ]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(10);
      expect(item.quality).toBe(21);
    });

    it('increases Backstage passes quality by 2 when sellIn is 10 or less', () => {
      const gildedRose = new GildedRose([
        new Item('Backstage passes to a TAFKAL80ETC concert', 10, 20),
      ]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(9);
      expect(item.quality).toBe(22);
    });

    it('increases Backstage passes quality by 3 when sellIn is 5 or less', () => {
      const gildedRose = new GildedRose([
        new Item('Backstage passes to a TAFKAL80ETC concert', 5, 20),
      ]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(4);
      expect(item.quality).toBe(23);
    });

    it('drops Backstage passes quality to 0 after the concert', () => {
      const gildedRose = new GildedRose([
        new Item('Backstage passes to a TAFKAL80ETC concert', 0, 20),
      ]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(-1);
      expect(item.quality).toBe(0);
    });

    it('caps Backstage passes quality at 50 in the 5-day window', () => {
      const gildedRose = new GildedRose([
        new Item('Backstage passes to a TAFKAL80ETC concert', 5, 49),
      ]);

      const [item] = gildedRose.updateQuality();

      expect(item.sellIn).toBe(4);
      expect(item.quality).toBe(50);
    });
  });
});
