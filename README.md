
# Brick Ball Game

## 1. Compilation Instructions: 

Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

javafx.media

## 2. Implemented and Working Properly
### Section 1: Additional Features

### a. Pause Function

Press "P" key to pause the game.

Upon pressing "P," a pause menu appears.

Pause Menu Options:
- Resume: Allows the player to continue the game from where it was paused.
- Restart Level: Restarts the current level.
- Back To Main Menu: Takes the player back to the main menu.

Player can also press "P" key again while in the pause menu to resume the game.

Auto-Pause on Frame Loss: The game detects frame loss or window focus loss and automatically pauses the game.

Reason: Allowing players to pause the game which will give them control over their gaming experience. 


### b. Sound Effects

Sound effects will be triggers when the condition occurs.

- Ball Hit Block: "Tok" triggers when the ball collides with a block.
- Bonus: "Ding" triggers when the player gains a bonus with the break.
- Penalty: "Boom" triggers when a receive the boom (penalty) with the break.
- Button Pressed: "Clak" triggers when player press on any buttons in the game.

Reason: Sound effects provide immediate feedback to players about the actions they perform or the events happening in the game. 

### c. Background Music (BGM)

BGM starts playing when the player first starts the game.

Looping Feature:
BGM automatically loops when it reaches the end, providing a seamless and uninterrupted musical experience.

Volume Control on BGM:
- BGM starts at a default volume level
- Player can press "M" key to open the volume control menu throughout the game.
- A slider in the volume control menu allows the player to adjust the loudness of the BGM.
- Checkbox in the volume control menu enables the player to mute or unmute the BGM.
- Offers flexibility for players who may want to enjoy the game with or without music.

Reason: Background music contributes significantly to setting the atmosphere and ambiance of the game. 
### d. Main Menu Page

When the player first runs the game, a main menu page is displayed, offering several button options to perfrom different actions.

- Start
- Instruction
- Highscore

Set the game elements invisible while selecting the game mode.

Reason: The main menu provides a clear and organized interface for players to navigate through various sections of the game.
### e. Instruction Page

When the player selects "Instruction" in the main menu, the player will be directed to the Instruction Page.

Key Controls Display:
Clearly presents keyboard and mouse controls required for different in-game mechanisms.
Utilizes icons to accompany textual descriptions of controls.

Back to Main Menu:
Includes a button "Back to Main Menu" button on the instruction page after reviewing the instructions.

Reason: Provides players with a comprehensive understanding of how to interact with the game.
### Level Selection Page

Accessed when the player selects "Level Select" in the game scene start page.

- Show different button corresponding to diffeent levels
- Permits the player to select a level equal to or below the saved level.
- Default settings with a score of 0 and 3 hearts to maintain fairness for highscore purposes.

Added a "Back To Main Menu" button to go back to main menu.


### f. High Score Implementation

When the player loses (runs out of hearts) or wins the game （complete all levels）, a dialog box pops up if the player achieves a highscore within the top 3.

The dialog box prompts the player to enter their name to associate with the highscore.

After entering the name, an alert box informs the player that they can view the highscore in the Highscore Page.

If the player presses cancel or doesn't input a name, the highscore is not saved.

The player's name and score are saved in a highscore.mdds file.
Allows the game to retain highscores even if the player closes and reopens the game.

High Score Page:
Accessible from the main menu, the highscore page displays the top 3 names with their corresponding highscores.

No Record: If no highscores exist, the highscore page displays a message indicating "No Record."

Back to Main Menu:
Includes a button "Back to Main Menu" button on the high score page after reviewing the high score list.

Reason: This feature adds a sense of achievement and encourages players to compete for top positions.





### g. Game Scene Start Page

Accessed when the player selects "Start" in the main menu. Displays buttons for different game options within the game scene.

In the initial implementation there is only a button "New Game" which allows user to start the game from level 1.

#### New Buttons added:

- Back to Main Menu

If there is a saved game process, displays two additional buttons.

- Level Selection
- Load Game

Reason: Including these additional buttons within the game scene enhances user convenience. 
### h. Buttons

- Added Back To Main Menu Button in different page to direct user back to main menu.

- When the player hovers over the buttons, a visual hover effect is triggered. This applys to all the buttons available in the game.

Reason: Ensures a consistent and easily accessible way for players to return to the main menu. Hover effects signals to players that the buttons are responsive and ready for interaction. 
### i. Mouse Drag Handler

Added mouse control functionality to the game, allowing the player to interact with the game using the mouse.

Break Movement:
The player can drag and move the break using the mouse.
Statements added to ensure the break is not out of bounds.

Reason: Enhances user experience by providing a more flexible method of controlling the game elements. 
### j. Restart Level

Implemented the ability for the player to restart the current level.

Key Control (R):
The player can press the "R" key to initiate the restart of the current level.

Pause Menu Option:
Added a "Restart Level" option in the Pause Menu.
Provides an alternative method for restarting the level during gameplay.

The level now begins with the initial heart count and score when the player first enters this level.

Reason: Allows players to reset the level voluntarily, providing an alternative method to address challenges or mistakes during gameplay. 
### k. Penalty Block Implementation

Introduced a new block type that, when hit, triggers a penalty in the form of a falling bomb which will only be generated starting from level 5.

Implemented the animation that when the penalty block is hit, the bomb will start falling from the block to the bottom of the screen.

Incorporates a "boom" sound effect when receive the penalty.

Deduct Marks:
If the player receives the bomb with the break, deducts 2 marks as a penalty.

Visual Indicator for Penalty:
Add a score deduction animation display, to highlight the penalty.
Ensures players are aware of the score deduction.

Reason: Adds complexity to the game, enhancing its overall difficulty and progression. 
### l. New Levels

Expanded the game with new levels introduced after the default level 17.

Level 18:
Introduced a new level (18) featuring 10 rows of bonus elements.
Provides players with an opportunity to earn extra points and rewards.

Level 19:
Introduced another new level (19) with a unique challenge: 10 rows of penalty elements.
Adds a strategic challenge for players to navigate through without receiving penalties.

Reason: Providing players with fresh content and challenges beyond the initial levels.
### Section 2: Fixing Bugs
### a. Load Game

Addressed the bug where initially, the game could be saved but not loaded.

- Implemented a statement to check if the save.mdds file exists at the beginning of the game.

- If the save.mdds file exists, the "Load Game" button is displayed on the game scene start page to resume from saved progress.

Initially, the saved destroy block count was not initialized to 0. 
- When loading the game, the destroy count remains but the block size is recalculated, leading to errors when proceeding to the next level. 
- To resolve this issue, the destroy block count is now explicitly set to 0 during the save game process.

Reason: Enable player to to resume from saved progress.
### b. Ball Hit Mechanism

Addressed the bug where initially, the ball failed to hit and destroy blocks sometimes as only the central point of the ball's circumference triggered block destruction.

Modified the block destruction mechanism to consider the entire ball radius for collision detection.
Blocks are now destroyed when any part of the ball, within its radius, hits the block.

Adjusted the collision detection for the ball's interaction with the screen boundary (wall).
Ensures that the entire ball is displayed within the screen, preventing parts of the ball from being outside the visible area.

Correct logic error for collideToLeftBlock, set the goRightBall to false.

Reason: Ensures a more accurate and consistent gameplay experience.
### c. Initialize Ball Position

Addressed the issue of ball initialization outside the screen.

Implemented a solution to ensure consistent ball generation.
The ball is now consistently generated at a fixed x-position.
Ensures the ball no longer goes out of bounds upon initialization.

Reason: Prevent the ball fall out of bound.
### d. Game Stage Size

Initially, the stage was resizable, leading to an unsightly display of the game when resized.

Maintained a default setting throughout all scene to prevent resizing of the game stage.

Reason: Ensures that the game stage remains fixed in size to prevent incomplete game visuals.
### e. Animation Timer 

Initially, utilizing threads for the game engine resulted in numerous errors. 

To address this issue, the implementation has been updated to use an animation timer instead. 

Reason: Avoids concurrent execution of threads, preventing collisions and associated errors in the game.
### f. Score Animation

The score label may not completely disappear from the screen initially. 

Remove the score label from the root after it appears.

Reason: Maintain a tidy and focused game interface, preventing unnecessary visual elements from lingering on the screen.
### g. Break Out of Bounds

The break may extend beyond the bounds of the screen when moving left and right, causing it to disappear. 

To address this issue, incorporate statements that ensure the break remains within the specified boundaries.

Reason: Preventing unintended consequences, such as losing control of the break.
### h. Time Factor Related Bugs

When the pause feature is integrated, issues arise, such as the game's timer continuing to run even when paused. 

This can result in abnormal behavior for the gold root and bonus, causing extended gold root times, as well as bonuses moving in the opposite direction (towards the top of the screen) instead of falling down.

To address this issue, pause the execution of the timer when the game is paused and resume it when the game is resumed. 

Reason: This ensure proper synchronization and prevent unexpected behavior associated with the gold root and bonus elements.
### Section 3: Refactor

### a. Spelling Mistakes

Corrected the spelling mistakes in the code.

For example: isGoldStatus, SceneHeight, Collide

Reason: To ensure readability.


### b. Change Variables to final

Declare some of the variabes as final, as its value cannot be changed once it's assigned. 

Reason: This promotes immutability, making the code more predictable and preventing unintended modifications to the variable.

### Delete unused statement

- Delete unused import libraries and variables.

- For example: the method setFps in GameEngine is deleted as animation timer is used to control the time.

### c. Resources

Split the images and css files into different folders based on their usage.

For example: 
- Instruction-related images and CSS are grouped in the 'instruction' folder.
- Highscore-related images and CSS are grouped in 'high-score' folder.
- Bonus, penalty, ball, break, block are grouped in 'game-elements' folder.
- Sound tracks are group in 'sound-effects' folder.
- Background images during gameplay are grouped in 'in-game-background' folder.
- The CSS for level selection page is grouped in 'level-selection' folder.
- The CSS for pause menu is grouped in 'pause-menu' folder.
- The images and CSS for main menu is grouped in 'main-menu' folder.

This provides a clear and organized structure to the project.




### Breaking into smaller parts

#### Breaking into Methods

- Break the big methods into smaller, well-organized methods.

#### Breaking into Classes

- Break the methods into different class based on their usage.


#### Breaking into Packages

- Initially there is only one package brickGame.

- Break the classes in brickGame into different packages based on their usages.

- To search for the relevent class easily.


Reason: Organizing and structuring the code in a modular and maintainable way.
### Iterator

Iterator is added whenever there is array.

For example: Array of blocks.

Reason: Iterators provide a uniform way to iterate over various types of collections in codebase. This promotes consistency and makes the code more predictable and readable.


### Added Game Design

CSS is added for all different scene. 
Using images for background instead of colors.
Add icon and title for the game stage.

Reason: To enhance the visual appeal and user experience of the game.


### Added a null check

Add null check (!= null) in statements that might produce null pointer exception before executing it.

Reason: To prevent potential errors when working with objects that might be null. 


### Win Page

Change the button at the win page to "Back To Main Menu" button from "Restart" button.

Removing the contents in the root for a clearer page.

Reason: After winning a game, players might naturally want to return to the main menu to explore other options instead of starting a new game. 

### Rename classes

Renaming classes with more descriptive names that reflect their purpose.

Reason: To create a clean and maintainable code.
### Use Design Patterns

#### Factory Design Pattern for Block

- Created an interface BlockFiller that defines a method applyFill, and concrete implementations for different block types.
- The BlockFillerFactory class is responsible for creating instances of the appropriate BlockFiller based on the block type.
-  By centralizing the creation logic in the factory, you can easily add new block types without modifying existing code. 
- This makes the system adaptable to changes and additions in the future.

#### Form Template Pattern for Bonus and Penalty

- Create 'Power' class, which is an abstract class that defines a template for creating instances of power-ups in the game. 
- Extract the common functionality into a base class for penalty and bonus.
- The template method outlines the steps for creating a power-up, and it delegates the implementation of certain steps to subclasses.
- This can avoid duplicated codes since bonus and penalty have similiar structure.

#### MVC Pattern for New Scenes
### 3. Implemented but Not Working Properly

List any features that have been implemented but are not working correctly. Explain the issues you encountered, and if possible, the steps you took to address them.
### 4. Features Not Implemented

#### Multiplayer Mode

Unable to implement multiplayer mode in the game, which players can compete among themselves to achieve high score.

Reason on why they are left out:
- Implementing multiplayer features involves managing connections between a server and multiple clients, handling real-time data synchronization, and ensuring a smooth and secure communication flow.
- Lack of knowledge and understanding of server-client architecture. 
- Limited time for the coursework to investigate fully regarding how the architecture works.


#### Turning Blocks

Unable to implement turning blocks, which the blocks will dynamically rotate at its own position.

Reason on why they are left out:
- Implementing dynamic block rotation involves complex mathematical calculations and graphical manipulations, to check whether the ball hit the blocks. 
- Due to these technical challenges and limited time to explore further on this idea.

#### Time-based Mode

Time-based mode not implement, which the player have to complete the level in limited time to proceed to next level.

Reason on why they are left out:
- The decision to prioritize core features took precedence over the introduction of time-based mechanics.
- Focus on features such as scoring mechanism, sound effects to enhance the fundamental gameplay experience.
- The implementation of a time-based mode remains a potential feature for inclusion in future projects with extended timelines.
### 5. New Java Classes

Enumerate any new Java classes that you introduced for the assignment. Include a brief description of each class's purpose and its location in the code.
### 6. Modified Java Classes

List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.
### 7. Unexpected Problems

Communicate any unexpected challenges or issues you encountered during the assignment. Describe how you addressed or attempted to resolve them.

