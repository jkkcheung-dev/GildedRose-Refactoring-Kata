# Updater Strategy Extraction Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Move item-specific update behavior out of `GildedRose` into updater/strategy objects while keeping `Item` unchanged and preserving current observable behavior.

**Architecture:** Keep `Item` as a dumb data object and introduce a small updater interface plus one updater implementation per item category. `GildedRose` becomes a selector/dispatcher that chooses an updater by item name and calls its `update(item)` method. Implement this incrementally by first extracting the contract and selector, then moving each existing per-item function body into updater classes without changing behavior.

**Tech Stack:** TypeScript, Node.js, Vitest

---

### File Structure

**Files:**
- Create: `app/item-updater.ts`
- Create: `app/updaters/normal-item-updater.ts`
- Create: `app/updaters/aged-brie-updater.ts`
- Create: `app/updaters/backstage-pass-updater.ts`
- Create: `app/updaters/sulfuras-updater.ts`
- Modify: `app/gilded-rose.ts`
- Modify: `test/vitest/gilded-rose.spec.ts`

**Responsibilities:**
- `app/item-updater.ts`: shared updater contract and exported item-name constants used by selector/updaters.
- `app/updaters/*.ts`: one behavior unit per item category, each implementing `update(item: Item): void`.
- `app/gilded-rose.ts`: keep `Item` definition and public `GildedRose` API, replace in-class item behavior with updater selection and delegation.
- `test/vitest/gilded-rose.spec.ts`: keep current characterization tests green and add any selector-focused coverage if needed.

### Task 1: Extract Shared Contract And Constants

**Files:**
- Create: `app/item-updater.ts`
- Modify: `app/gilded-rose.ts`
- Test: `test/vitest/gilded-rose.spec.ts`

- [ ] **Step 1: Write the failing test**

Add this test to `test/vitest/gilded-rose.spec.ts` near the existing `Sulfuras` group:

```ts
  it('does not change sellIn or quality for Sulfuras after updater extraction', () => {
    const gildedRose = new GildedRose([new Item('Sulfuras, Hand of Ragnaros', 0, 80)]);

    const [item] = gildedRose.updateQuality();

    expect(item.sellIn).toBe(0);
    expect(item.quality).toBe(80);
  });
```

Purpose: create an explicit regression anchor before changing dispatch wiring.

- [ ] **Step 2: Run test to verify current state**

Run: `npx vitest run test/vitest/gilded-rose.spec.ts`

Expected: PASS. This is a characterization anchor, not a red test, because current behavior already exists. Note that in task log.

- [ ] **Step 3: Write the shared contract and constants**

Create `app/item-updater.ts` with:

```ts
import type { Item } from '#app/gilded-rose';

export const AGED_BRIE = 'Aged Brie';
export const BACKSTAGE_PASSES = 'Backstage passes to a TAFKAL80ETC concert';
export const SULFURAS = 'Sulfuras, Hand of Ragnaros';

export interface ItemUpdater {
  update(item: Item): void;
}
```

Then update `app/gilded-rose.ts` imports to use these exported constants instead of file-local constants.

- [ ] **Step 4: Run full suite to verify it still passes**

Run: `npm test`

Expected: PASS, `15` tests passed.

- [ ] **Step 5: Commit**

```bash
git add app/item-updater.ts app/gilded-rose.ts test/vitest/gilded-rose.spec.ts
git commit -m "refactor(typescript): extract updater contract"
```

### Task 2: Move Normal Item Behavior To Strategy Object

**Files:**
- Create: `app/updaters/normal-item-updater.ts`
- Modify: `app/gilded-rose.ts`
- Test: `test/vitest/gilded-rose.spec.ts`

- [ ] **Step 1: Write the failing test**

Add this test to the `normal items` describe block in `test/vitest/gilded-rose.spec.ts`:

```ts
  it('keeps normal item behavior unchanged after strategy extraction', () => {
    const gildedRose = new GildedRose([new Item('normal item', 0, 2)]);

    const [item] = gildedRose.updateQuality();

    expect(item.sellIn).toBe(-1);
    expect(item.quality).toBe(0);
  });
```

Purpose: cover both sellIn decrement and double degradation at expiry in one direct example.

- [ ] **Step 2: Run test to verify current state**

Run: `npx vitest run test/vitest/gilded-rose.spec.ts`

Expected: PASS.

- [ ] **Step 3: Write minimal strategy implementation**

Create `app/updaters/normal-item-updater.ts` with:

```ts
import type { ItemUpdater } from '#app/item-updater';
import type { Item } from '#app/gilded-rose';

export class NormalItemUpdater implements ItemUpdater {
  update(item: Item) {
    if (item.quality > 0) {
      item.quality = item.quality - 1;
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0 && item.quality > 0) {
      item.quality = item.quality - 1;
    }
  }
}
```

Modify `app/gilded-rose.ts` so the default path in `updateItem(...)` delegates to a cached `NormalItemUpdater` instance instead of calling in-class normal-item logic.

- [ ] **Step 4: Run full suite to verify it passes**

Run: `npm test`

Expected: PASS, `15` tests passed.

- [ ] **Step 5: Commit**

```bash
git add app/updaters/normal-item-updater.ts app/gilded-rose.ts test/vitest/gilded-rose.spec.ts
git commit -m "refactor(typescript): extract normal updater"
```

### Task 3: Move Aged Brie And Sulfuras Behavior

**Files:**
- Create: `app/updaters/aged-brie-updater.ts`
- Create: `app/updaters/sulfuras-updater.ts`
- Modify: `app/gilded-rose.ts`
- Test: `test/vitest/gilded-rose.spec.ts`

- [ ] **Step 1: Write the failing test**

Add this test to the `Aged Brie` describe block in `test/vitest/gilded-rose.spec.ts`:

```ts
  it('keeps Aged Brie expiry behavior unchanged after strategy extraction', () => {
    const gildedRose = new GildedRose([new Item('Aged Brie', 0, 48)]);

    const [item] = gildedRose.updateQuality();

    expect(item.sellIn).toBe(-1);
    expect(item.quality).toBe(50);
  });
```

Purpose: verify cap-at-50 while still applying both increases around expiry.

- [ ] **Step 2: Run test to verify current state**

Run: `npx vitest run test/vitest/gilded-rose.spec.ts`

Expected: PASS.

- [ ] **Step 3: Write minimal strategy implementations**

Create `app/updaters/aged-brie-updater.ts` with:

```ts
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
```

Create `app/updaters/sulfuras-updater.ts` with:

```ts
import type { ItemUpdater } from '#app/item-updater';
import type { Item } from '#app/gilded-rose';

export class SulfurasUpdater implements ItemUpdater {
  update(_item: Item) {}
}
```

Modify `app/gilded-rose.ts` so `AGED_BRIE` and `SULFURAS` dispatch through cached updater instances.

- [ ] **Step 4: Run full suite to verify it passes**

Run: `npm test`

Expected: PASS, `15` tests passed.

- [ ] **Step 5: Commit**

```bash
git add app/updaters/aged-brie-updater.ts app/updaters/sulfuras-updater.ts app/gilded-rose.ts test/vitest/gilded-rose.spec.ts
git commit -m "refactor(typescript): extract brie updaters"
```

### Task 4: Move Backstage Pass Behavior

**Files:**
- Create: `app/updaters/backstage-pass-updater.ts`
- Modify: `app/gilded-rose.ts`
- Test: `test/vitest/gilded-rose.spec.ts`

- [ ] **Step 1: Write the failing test**

Add this test to the `Backstage passes` describe block in `test/vitest/gilded-rose.spec.ts`:

```ts
  it('keeps Backstage passes expiry transition unchanged after strategy extraction', () => {
    const gildedRose = new GildedRose([
      new Item('Backstage passes to a TAFKAL80ETC concert', 0, 49),
    ]);

    const [item] = gildedRose.updateQuality();

    expect(item.sellIn).toBe(-1);
    expect(item.quality).toBe(0);
  });
```

Purpose: pins the most sensitive backstage transition before extracting final behavior.

- [ ] **Step 2: Run test to verify current state**

Run: `npx vitest run test/vitest/gilded-rose.spec.ts`

Expected: PASS.

- [ ] **Step 3: Write minimal strategy implementation**

Create `app/updaters/backstage-pass-updater.ts` with:

```ts
import type { ItemUpdater } from '#app/item-updater';
import type { Item } from '#app/gilded-rose';

export class BackstagePassUpdater implements ItemUpdater {
  update(item: Item) {
    if (item.quality < 50) {
      item.quality = item.quality + 1;
    }
    if (item.sellIn < 11 && item.quality < 50) {
      item.quality = item.quality + 1;
    }
    if (item.sellIn < 6 && item.quality < 50) {
      item.quality = item.quality + 1;
    }

    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0) {
      item.quality = item.quality - item.quality;
    }
  }
}
```

Modify `app/gilded-rose.ts` so `BACKSTAGE_PASSES` dispatches through the cached updater instance.

- [ ] **Step 4: Remove dead in-class behavior and run full suite**

After all item categories dispatch through updater instances, remove any dead in-class item-specific update methods from `app/gilded-rose.ts`.

Run: `npm test`

Expected: PASS, `15` tests passed.

- [ ] **Step 5: Commit**

```bash
git add app/updaters/backstage-pass-updater.ts app/gilded-rose.ts test/vitest/gilded-rose.spec.ts
git commit -m "refactor(typescript): extract backstage updater"
```

### Task 5: Tighten Dispatch And Verify Final Shape

**Files:**
- Modify: `app/gilded-rose.ts`
- Test: `test/vitest/gilded-rose.spec.ts`

- [ ] **Step 1: Add final selector-focused regression test**

Add this test near the `basic behavior` block in `test/vitest/gilded-rose.spec.ts`:

```ts
  it('still routes unknown item names through normal-item behavior', () => {
    const gildedRose = new GildedRose([new Item('foo', 0, 2)]);

    const [item] = gildedRose.updateQuality();

    expect(item.sellIn).toBe(-1);
    expect(item.quality).toBe(0);
  });
```

- [ ] **Step 2: Run targeted test to verify current state**

Run: `npx vitest run test/vitest/gilded-rose.spec.ts`

Expected: PASS.

- [ ] **Step 3: Simplify selector code if possible**

If `app/gilded-rose.ts` still has temporary selector noise, reduce it to a small cached updater map or a single `switch` that only chooses updater instances. Keep `Item` and `GildedRose` public API unchanged.

Example acceptable end-state inside `GildedRose`:

```ts
  private readonly normalUpdater = new NormalItemUpdater();
  private readonly agedBrieUpdater = new AgedBrieUpdater();
  private readonly backstagePassUpdater = new BackstagePassUpdater();
  private readonly sulfurasUpdater = new SulfurasUpdater();

  private getUpdater(item: Item): ItemUpdater {
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
```

- [ ] **Step 4: Run full verification**

Run: `npm test && npm run build`

Expected: PASS for both commands.

- [ ] **Step 5: Commit**

```bash
git add app/gilded-rose.ts test/vitest/gilded-rose.spec.ts app/item-updater.ts app/updaters/*.ts
git commit -m "refactor(typescript): route items through updaters"
```
