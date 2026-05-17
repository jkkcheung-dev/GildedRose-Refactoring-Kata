# Incremental Refactor Design

**Goal:** Refactor `TypeScript - my completed version/` incrementally toward clearer structure while preserving current observable behavior and explicitly deferring any `Conjured` behavior change.

## Scope
- In scope: `TypeScript - my completed version/` only.
- In scope: requirements reading, current behavior mapping, incremental test addition, small seam creation only when needed, deeper refactoring after behavior coverage is strong enough.
- Out of scope: changing behavior to satisfy missing `Conjured` requirement, changing `Item` shape, changing `items` property shape, assuming patterns from sibling language folders.

## Verified Context
- Requirements come from root `GildedRoseRequirements.md`.
- Current TypeScript entrypoint is `app/gilded-rose.ts`.
- Executable test runner is Vitest via `npm test`; local README is stale about Jest/Mocha.
- Broad approval coverage exists through `test/vitest/approvals.spec.ts`, which snapshots both a trivial example and 30-day console output from `test/golden-master-text-test.ts`.
- Current text fixture explicitly comments that `Conjured` does not work properly yet. This design treats that as current behavior to preserve during refactor-only phase.

## Constraints
- Preserve public model contract from kata requirements: do not alter `Item` class or `items` property shape.
- Before writing each test, prefer current public API. Do not modify production code unless next test or next safe refactor needs a seam.
- Do not introduce abstractions early. New helpers, classes, or patterns must be justified by duplication, branch noise, or repeated rule handling already covered by tests.
- Keep whole-system approval test green while adding targeted tests.

## Recommended Working Loop
1. Read requirements and existing broad behavior sources.
2. Survey code broadly to identify rule branches and repeated logic.
3. Add focused tests for most observable pure behaviors first, using current public API.
4. Make smallest production change needed to support next test or local clarity improvement.
5. Refactor only within behavior protected by tests.
6. Repeat until current behavior is covered well enough to support deeper restructuring.
7. Only then consider stronger abstractions if code shape now clearly demands them.

## Behavior-First Test Order
Start with single-update behaviors that are easy to observe directly through `GildedRose.updateQuality()`:
- normal item before sell date
- normal item after sell date
- quality never below 0
- `Aged Brie` increases before and after sell date, capped at 50
- `Sulfuras` never changes `sellIn` or `quality`
- `Backstage passes` increase at >10, <=10, <=5 thresholds
- `Backstage passes` drop to 0 after concert
- quality never above 50 except existing `Sulfuras` value 80

Later tests should capture behaviors discovered during refactoring when current code reveals non-obvious interactions.

## Refactor Stages

### Stage 1: Behavior Inventory
- Re-read requirements and compare them against current `updateQuality()` branches.
- Identify observable rules, branch conditions, repeated checks, and mixed responsibilities inside loop.
- Keep notes tied to exact code locations and tests to add.

### Stage 2: Focused Test Net
- Add small, direct tests for pure behaviors first.
- Use approval snapshot as backstop, not replacement for targeted rule tests.
- Avoid test helpers that hide item setup until repeated setup becomes real friction.

### Stage 3: Minimal Seam Creation
- Only when next step needs it, make tiny structural moves such as:
  - local variable for current item
  - small private helper for duplicated rule block
  - named predicate for repeated item-category checks
- Each seam should pay for itself immediately in next test or next local refactor.

### Stage 4: Local Simplification
- Reduce nested conditionals gradually.
- Separate concerns inside update flow: item category decision, quality adjustment, sellIn adjustment, post-expiry adjustment.
- Keep changes small enough that failures map to one idea.

### Stage 5: Deeper Reshaping
- After behavior coverage is broad enough, consider clearer decomposition such as per-rule helpers or dedicated updater objects.
- Only introduce OOP polymorphism, inheritance, or patterns if repeated rule dispatch and branching still make code harder to understand after simpler refactors.
- Prefer simplest structure that makes future feature addition easier.

## Verification Strategy
- Frequent command: `npm test` from `TypeScript - my completed version/`.
- Use `npm run test:watch` during tight test/refactor loops when useful.
- Use snapshot updates only intentionally; in refactor-only phase, unexpected snapshot diff means likely regression.
- Use `npm run build` after structural refactors that might expose TypeScript issues beyond tests.

## Risks
- Existing whole-system snapshot can make accidental behavior changes visible, but not easy to diagnose without focused unit tests.
- Current code keeps all rules in one large method with repeated indexing and nested branching; careless extraction could change timing of `sellIn` and `quality` updates.
- Requirements mention `Conjured`, but this design intentionally defers implementing it.

## Success Criteria
- Test suite covers current observable behaviors with readable focused tests, not only snapshots.
- Production code becomes easier to follow in small steps, with abstractions introduced only after proven need.
- Public model shape remains compatible with kata constraint.
- Refactor-only phase ends with preserved current behavior, clearer code, and safer ground for later feature work.
