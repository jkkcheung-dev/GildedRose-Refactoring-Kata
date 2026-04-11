# Gilded Rose Refactoring Challenge

This directory contains my finished solution.  
Visit the `Java` directory for the original challenge starting point.

## Approach

This README is mainly for my own reference: how I approached the refactoring, what I learned, and what I would do again in a similar interview or kata setting.

## Key ideas

1. Start by understanding what the challenge is really asking.

   A useful introduction is this talk by Emily Bache:  
   https://www.youtube.com/watch?v=8bZh5LMaSmE

   One important assumption is that the original messy business logic is already functioning. The goal is not to redesign the requirements from scratch, but to refactor working code safely.

   That said, be careful: the version shown in the video is simpler than this Java version. In particular, edge cases around `quality` are easy to miss. For example:
   - `quality` should never become negative.
   - `quality` should never become greater than 50.
   - Even if sample tests pass, that does not mean the behavior is fully correct.

2. Write accurate and meaningful unit tests first.

   If this is done in an interview, ask clarifying questions whenever the requirement document is vague or incomplete. That ambiguity is often intentional.

   Also, do not trust “mostly passing” tests too early. If edge cases are missing, your tests may only prove that the code works in common paths, not that it is correct.

3. If AI assistance is allowed, use it carefully.

   A practical workflow is:
   - Open the project in VS Code, IntelliJ, or CLI tools.
   - Write your own tests first based on the requirement document.
   - Then use AI to help identify missing edge cases.

   Before implementing `Conjured`, all other tests should already be green. That gives you a safe baseline before making the intended change.

## Refactoring strategy

4. There are at least two valid ways to begin.

   **Option 1:** Ignore the old structure and rewrite the business rules in a cleaner way for each item type.

   **Option 2:** Copy the original logic for each magic string into separate blocks, then simplify each branch step by step by manually evaluating the conditions.

   A useful reference for the second style is:  
   https://www.youtube.com/watch?v=eKHzZ-EooTg

   Both approaches have trade-offs. Either way, the goal at this stage is the same: isolate the behavior for each item type so most of the original nested logic can disappear.

5. Do not abstract too early.

   Once the item-specific methods are separated, you will probably notice duplication. That is normal.

   Keep the duplication for a while. Premature abstraction can create the wrong design and make later refactoring harder. Wait until the item behaviors are clearly visible.

6. Move each behavior into its own class.

   At this point, each item-specific method starts to look like a class with the same public operation but different internal logic.

   That is a good signal to:
   - create one class per item type
   - move the business rules into that class
   - update the main flow so it delegates to the correct implementation

   Initially, this may still look like a `switch`-based dispatch.

7. Introduce a shallow inheritance structure.

   Once the separate classes exist, they naturally resemble different kinds of items. So it makes sense to model them as children of `Item`.

   This gives a narrow and shallow inheritance structure, which is appropriate here. The code becomes easier to read because each class represents one business concept.

8. Be aware of the challenge constraint.

   In this version of the kata, we should not modify the `Item` class itself.

   In a normal OO design, I would prefer `Item` to define an overridable method such as `updateQuality()`, then let subclasses override it. But since `Item` is not supposed to change, that option is limited here.

9. Add supporting structure when needed.

   Because `Item` cannot be changed, it can be useful to introduce:
   - an `ItemFactory`
   - optionally an updater or dispatcher class

   If `Item` were editable, some of this structure could be simplified.

## Factory design

10. Replace hard-coded construction with a map-based factory.

   One idea is to map item names to classes and instantiate them dynamically at runtime.

   That is possible, but a cleaner and safer modern Java approach is to store constructor functions in a map and call them directly, instead of using reflection.

   In other words:
   - avoid mapping strings to class-name strings
   - avoid reflective instantiation unless it is really necessary
   - prefer a map of constructor references

   This keeps the code type-safe and easier to maintain.

## Final result

11. Most of the effort is not adding the new feature.

   The real work is making future changes easier.

   Roughly speaking:
   - 95% of the effort is spent cleaning the structure
   - 5% is spent adding the actual new behavior

12. After the structure is clean, `Conjured` becomes easy.

   At that point, you can create the `Conjured` class, implement the specific rules from the requirement document, and run the tests.

   If the earlier refactoring was done carefully, all tests should pass.