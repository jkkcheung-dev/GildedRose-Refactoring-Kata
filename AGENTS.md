# Gilded Rose Refactoring Kata

## Scope
- Verified from root `README.md`, root `GildedRoseRequirements.md`, and `TypeScript - my completed version/` only. Do not assume sibling language folders share same tooling or workflow without checking them.

## Project Shape
- Repo is kata, not single app. Root `README.md` describes many language variants; requirements live in root `GildedRoseRequirements.md`.
- Core kata rule from requirements: preserve `Item` class and `Items`/`items` property shape. Refactor around `updateQuality`, not through breaking model API.
- Required behavior includes `Conjured` items degrading twice as fast as normal items. In `TypeScript - my completed version/test/golden-master-text-test.ts`, comment says conjured behavior is not implemented yet; treat that as intentional warning, not proof requirements changed.

## TypeScript Subproject
- Main TypeScript work happens in `TypeScript - my completed version/`.
- Real logic entrypoint: `TypeScript - my completed version/app/gilded-rose.ts` exporting `Item` and `GildedRose`.
- Package import alias `#app/*` maps to `./app/*.ts` via `TypeScript - my completed version/package.json`; tests import `#app/gilded-rose`, not relative paths.
- TypeScript config uses `module`/`moduleResolution` = `nodenext`, target `ES2022`, strict mode on, `noImplicitAny: false`.

## Commands
- Install deps in TypeScript folder: `npm install`
- Main verification in TypeScript folder: `npm test`
  Runs `pretest` first, which deletes compiled JS maps and `coverage/`. Do not depend on generated artifacts surviving test runs.
- Watch mode: `npm run test:watch`
- Compile TypeScript: `npm run build`
  `prebuild` also runs clean first.
- Golden master CLI: `npm run texttest -- 30`
  Equivalent script calls `ts-node test/golden-master-text-test.ts`.

## Tests And Approval Flow
- Vitest is actual configured runner. Root/local README still mentions Jest and Mocha, but `TypeScript - my completed version/package.json` only defines Vitest scripts. Trust package scripts, not stale README text.
- Vitest only includes `test/vitest/**/*.{spec,test}.ts` via `vitest.config.mts`. Files outside that tree, like `test/golden-master-text-test.ts`, are support fixtures, not direct Vitest tests.
- `test/vitest/approvals.spec.ts` snapshots two things:
  `should foo`: minimal unit-style snapshot.
  `should thirtyDays`: executes Node with `node_modules/ts-node/dist/bin.js` against `test/golden-master-text-test.ts` and snapshots full 30-day console output.
- If behavior intentionally changes, update Vitest snapshots with `npx vitest -u` from TypeScript folder.
- Coverage uses V8 with `text` and `html` reporters; output goes to `coverage/`, but `clean` deletes it.

## TextTest Quirk
- `TypeScript - my completed version/texttest_rig.py` is bridge for external TextTest fixture. It shells out to `node node_modules/ts-node/dist/bin.js test/golden-master-text-test.ts`.
- If using upstream TextTest workflow from root README, setup still depends on root `texttests/` config that was not inspected here. Verify before changing TextTest-specific files outside TypeScript folder.

## Reading Order
- For TypeScript work, read in this order:
  `GildedRoseRequirements.md` -> `TypeScript - my completed version/package.json` -> `TypeScript - my completed version/app/gilded-rose.ts` -> `TypeScript - my completed version/test/vitest/approvals.spec.ts` -> `TypeScript - my completed version/test/golden-master-text-test.ts`

## Risks / Unknowns
- Root repo likely contains many other language implementations and shared TextTest assets. None were inspected beyond root `README.md` and `GildedRoseRequirements.md`.
- Existing root `AGENTS.md`, CI config, and sibling-project instructions were not read due access constraint, so this file intentionally avoids repo-wide workflow claims beyond verified sources.
