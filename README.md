
# Brick Ball Game

## 1. Compilation Instructions: 

Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

## 2. Implemented and Working Properly
### Section 1: Additional Features

### a. Pause Function

Player can press "P" key to manually pause the game.
Upon pressing "P," a pause menu appears.

Pause Menu Options:
- Resume: Allows the player to continue the game from where it was paused.
- Restart Level: Restarts the current level from the beginning.
- Back To Main Menu: Takes the player back to the main menu.

Resume Functionality:
Player can also press "P" key again while in the pause menu to resume the game.

Auto-Pause on Frame Loss: 
The game detects frame loss or window focus loss and automatically pauses the game.

#### The related class and methods can be found in package pauseGame.

### b. Sound Effects

Sound effects will be triggers when the condition occurs.

- Ball Hit Block: "Tok" triggers when the ball collides with a block.
- Bonus: "Ding" triggers when the player gains a bonus with the break.
- Penalty: "Boom" triggers when a receive the boom (penalty) with the break.
- Button Pressed: "Clak" triggers when player press on any buttons in the game.

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
### d. Main Menu Page

When the player first runs the game, a main menu page is displayed, offering several button options to perfrom different actions.

- Start
- Instruction
- Highscore
### e. Instruction Page

When the player selects "Instruction" in the main menu, the player will be directed to the Instruction Page.

Key Controls Display:
Clearly presents keyboard and mouse controls required for different in-game mechanisms.
Provides players with a comprehensive understanding of how to interact with the game.
Utilizes icons to accompany textual descriptions of controls.

Back to Main Menu:
Includes a button "Back to Main Menu" button on the instruction page after reviewing the instructions.
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







### g. Game Scene Start Page

Accessed when the player selects "Start" in the main menu. Displays buttons for different game options within the game scene.

In the initial implementation there is only a button "New Game" which allows user to start the game from level 1.

#### New Buttons added:

- Back to Main Menu

Saved Process Check:
If there is a saved game process, displays two additional buttons.

- Level Selection
- Load Game
### h. Buttons

Added Back To Main Menu Button in different page to direct user back to main menu.

When the player hovers over the buttons, a visual hover effect is triggered. This applys to all the buttons available in the game.
### i. Mouse Drag Handler

Added mouse control functionality to the game, allowing the player to interact with the game using the mouse.

Break Movement:
The player can drag and move the break using the mouse.
Statements added to ensure the break is not out of bounds.
### j. Restart Level

Implemented the ability for the player to restart the current level.

Key Control (R):
The player can press the "R" key to initiate the restart of the current level.

Pause Menu Option:
Added a "Restart Level" option in the Pause Menu.
Provides an alternative method for restarting the level during gameplay.

The level now begins with the initial heart count and score when the player first enters this level.
### k. Penalty Block Implementation

Introduced a new block type that, when hit, triggers a penalty in the form of a falling bomb which will only be generated starting from level 5.

Implemented the animation that when the penalty block is hit, the bomb will start falling from the block to the bottom of the screen.

Incorporates a "boom" sound effect when receive the penalty.

Deduct Marks:
If the player receives the bomb with the break, deducts 2 marks as a penalty.

Visual Indicator for Penalty:
Add a score deduction animation display, to highlight the penalty.
Ensures players are aware of the score deduction.
### l. New Levels

Expanded the game with new levels introduced after the default level 17.

Level 18:
Introduced a new level (18) featuring 10 rows of bonus elements.
Provides players with an opportunity to earn extra points and rewards.

Level 19:
Introduced another new level (19) with a unique challenge: 10 rows of penalty elements.
Adds a strategic challenge for players to navigate through without receiving penalties.
### Section 2: Fixing Bugs
### a. Load Game

Addressed the bug where initially, the game could be saved but not loaded.

- Implemented a statement to check if the save.mdds file exists at the beginning of the game.

- If the save.mdds file exists, the "Load Game" button is displayed on the game scene start page to resume from saved progress.

Initially, the saved destroy block count was not initialized to 0. 
- When loading the game, the destroy count remains but the block size is recalculated, leading to errors when proceeding to the next level. 
- To resolve this issue, the destroy block count is now explicitly set to 0 during the save game process.
### b. Ball Hit Mechanism

Addressed the bug where initially, the ball failed to hit and destroy blocks sometimes as only the central point of the ball's circumference triggered block destruction.

Modified the block destruction mechanism to consider the entire ball radius for collision detection.
Blocks are now destroyed when any part of the ball, within its radius, hits the block.

Adjusted the collision detection for the ball's interaction with the screen boundary (wall).
Ensures that the entire ball is displayed within the screen, preventing parts of the ball from being outside the visible area.
### c. Initialize Ball Position

Addressed the issue of ball initialization outside the screen.

Implemented a solution to ensure consistent ball generation.
The ball is now consistently generated at a fixed x-position.
Ensures the ball no longer goes out of bounds upon initialization.
### d. Game Stage Size

Initially, the stage was resizable, leading to an unsightly display of the game when resized.

Maintained a default setting throughout all scene to prevent resizing of the game stage.

Ensures that the game stage remains fixed in size to prevent incomplete game visuals.
### e. Animation Timer 

Initially, utilizing threads for the game engine resulted in numerous errors. 

To address this issue, the implementation has been updated to use an animation timer instead. 

This change avoids concurrent execution of threads, preventing collisions and associated errors in the game.
