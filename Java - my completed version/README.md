# This directory is my finished work, visit Java directory for the challenge starting point

## My thought on approaching this refactoring challenge

1. Watch this video first to get an idea about what this challenge want you to do : https://www.youtube.com/watch?v=8bZh5LMaSmE One assumption is that the business logic written in messy codes is actually functioning.

   All our work is around this working but messy code.

   Speaker was fantastic, but be careful.

   The challenge shown on the video was simpler than this one, and probably the one that you may encounter in tech interview. Tests are already written for you, and also the speaker ignored the edge case, like Quality could not be negative when being decreased, and could not be more than 50 when being increased. The interesting part was the tests shown on the video all passed. This brings us Point 2

2. We need to get ourselves accurate and meaningful unit tests. Ask clarifying questions during interview when you find requirement doc something seems missing or some words confusing. That is on purpose. Also make sure to consider edge case, or you will only have seemingly working unit tests.

3. If AI is allowed, open the project in VS Code or other IDE or CLI tools, write your own test first following the requirement doc, then ask AI to cover any edge case you miss. No need to move forward before all the tests except the ones for 'Conjured' are green. Since completing the business logic for 'Conjured' is exactly your task

4. We have 2 ways here.
   1. is to ignore the existing logic, directly rewrite the entire business logic for all the magic strings, make a method for each of them, then do a little refactoring on each method first
   2. is to copy and paste the full original method block for each magic string and refactor them one by one. Manually replace the item name with each string and evaluate the boolean expression. all the way to a state that you can no longer simplify. reference : https://www.youtube.com/watch?v=eKHzZ-EooTg
   Each way has their pros and cons. Anyway, at this stage you now should have a method for each string, and can replace 99% of the original messy code.

5. Now you may notice there are some duplication on business logic for each string, but keep that, you can get yourself a wrong abstraction and work on top of that if you abstract too early. Wait until you at least have a fundamental OO structure

6. Now you should notice each method for each string looks like some classes with the same method name, while different implementation. Create a class for each of the string, and move the business logic into a member method of each. Update codes accordingly in the original main method. Now we basically have a switch case to call the method of corresponding class

7. Now you notice each class looks like an item, so why not make them children of the Item ? We make a shallow and narrow inheritance. Now you notice that GildedRose class is actually using Item and its children to do some work. So the Item is just a role, it serves as a Factory pattern.

8. Things getting tricky here. We should be able to update the Item class itself to add an overriding method for children, but we cannot alter Item class in this modern gilded rose challenge. Otherwise, we can make the switch statement shorter by using just 1 method call

9. Now you might want to create a separate Factory class and an Updater class (won't be necessary if Item class contains the updateQuality method that children can override)

10. If possible to use a syntax that create a class by a string in runtime and then instantiate the class, you can even use a map data structure instead of hard-coded new statement for different classes in Factory
 Edit : A cleaner and safer modern Java approach is to store constructor functions in a map, then call them directly instead of converting strings into class names and instantiating with reflection

11. Finally, the code structure is clean, we spend 95% of time to make future change easier, then 5% time to make new easy changes

12. you can create the Conjured class and add the simple specific logic from the requirement doc. All the tests should pass now

