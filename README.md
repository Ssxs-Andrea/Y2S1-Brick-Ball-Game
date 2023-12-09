
# Brick Ball Game

## 1. Compilation Instructions: 
1. Download and extract the ZIP file.
2. Copy the path of the folder.
3. Open IntelliJ and select [Open].
4. Paste the path, select the folder and select [OK].
5. Load the maven script found if needed.
6. Download JavaFX from https://gluonhq.com/products/javafx/

        JavaFx Version: 21.0.1 [LTS]
        Operating System: Windows
        Architecture: x64
        Type: Sdk

7. After downloading the JavaFx, unzip the file. 
8. Copy the path of the folder.
9. In IntelliJ, select [File] -> [Project Structure] -> [Libraries] -> [+] -> [Java] 
10. Paste the path, select [lib] folder, press [OK] and [Apply]
11. If you do not have your SDK specified yet, in the [Project Structure], select [Project] -> [Sdk] -> [Download Jdk]
12. Make sure the location of the Sdk file is correct, press [Download] -> [Apply] -> [OK]

You are all set now.

Open the Main in brickGame package and run the program.

---
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
### d. Game Stage Size

Initially, the stage was resizable, leading to an unsightly display of the game when resized.

Maintained a default setting throughout all scene to prevent resizing of the game stage.

Reason: Ensures that the game stage remains fixed in size to prevent incomplete game visuals.
### Section 3: Refactor In General

### a. Spelling Mistakes

Corrected the spelling mistakes in the code.

For example: isGoldStatus, SceneHeight, Collide

Reason: To ensure readability.


### b. Change Variables to final

Declare some of the variabes as final, as its value cannot be changed once it's assigned. 

Reason: This promotes immutability, making the code more predictable and preventing unintended modifications to the variable.

### c. Delete unused statement

- Delete unused import libraries and variables.

### d. Resources

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

### e. Use MVC Pattern for New Scenes

- High Score, Main Menu, Pause Menu, Level Select are split into MVC pattern.
- Enforces a clear separation of concerns, dividing the scene into three main components: the Model (data), the View (user interface), and the Controller (input and interaction).
- This separation makes the codebase more modular and easier to maintain.

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
### Added Game Design

CSS is added for all different scene. 
Using images for background instead of colors.
Add icon and title for the game stage.

Reason: To enhance the visual appeal and user experience of the game.


### Added a null check

Add null check (!= null) in statements that might produce null pointer exception before executing it.

Reason: To prevent potential errors when working with objects that might be null. 
### 3. Implemented but Not Working Properly

---
#### Ball-block Collision 
- Ball cannot properly bounce back when hitting the middle of two blocks in a straight line, was encountered due to the ball's collision detection only considering one part of the block at a time (either left, right, top, or bottom). 
- Attempts were made to address this by adding conditions for multiple collision scenarios, but these were unsuccessful in fully resolving the issue.

---
#### Sound Effects for Ball-break Collision
- Trying to implement sound effect when the ball hits the break.
- The sound effect not playing sometimes while the ball hits the break.
- Attempts were undertaken to address the issue by adding conditions, but the desired consistency could not be achieved.
- Thus, the decision was made to remove the sound effect for ball-break collisions in the final implementation.

---
#### Flash of Previous Scnene During Scene Switch
- One issue encountered was a brief flash of the previous scene when switching scenes, when entering levels from the level select screen or while restarting levels, where the buttons of the game scene start page was briefly visible before the selected level scene appeared. 
- Efforts were made to address this issue by attempting to set the buttons invisible before the new scene loaded. 
- However, this approach did not fully eliminate the flash, and the issue still persisted.
### 4. Features Not Implemented

---
#### Multiplayer Mode

Unable to implement multiplayer mode in the game, which players can compete among themselves to achieve high score.

Reason on why they are left out:
- Implementing multiplayer features involves managing connections between a server and multiple clients, handling real-time data synchronization, and ensuring a smooth and secure communication flow.
- Lack of knowledge and understanding of server-client architecture. 
- Limited time for the coursework to investigate fully regarding how the architecture works.

---
#### Turning Blocks

Unable to implement turning blocks, which the blocks will dynamically rotate at its own position.

Reason on why they are left out:
- Implementing dynamic block rotation involves complex mathematical calculations and graphical manipulations, to check whether the ball hit the blocks. 
- Due to these technical challenges and limited time to explore further on this idea.

---
#### Time-based Mode

Time-based mode not implement, which the player have to complete the level in limited time to proceed to next level.

Reason on why they are left out:
- The decision to prioritize core features took precedence over the introduction of time-based mechanics.
- Focus on features such as scoring mechanism, sound effects to enhance the fundamental gameplay experience.
- The implementation of a time-based mode remains a potential feature for inclusion in future projects with extended timelines.
### 5. New Java Classes

---
Created a new highScore package.

New Class added in highScore package:

- HighScoreView: Represents the graphical user interface for displaying high scores, providing a visual representation of scores along with a back button for navigation.
- HighScoreManager: Manages the storage, retrieval, and updating of high scores, utilizing serialization to persistently store scores in a file, and providing methods to check, update, and format high scores.
- HighScoreController: Handles user interactions and events for the high scores, including checking and adding new high scores, linking to the main menu, and displaying relevant alerts and dialogs.
- HighScore: Represents a high score entry, storing the player's name and score, and implementing serialization for efficient storage and retrieval.

---
Created a new soundEffects package. 

New Class added in soundEffects package:

- BackgroundMusicPlayer: Manages the background music for the game, allowing for continuous playback and automatic restart when reaching the end.
- SoundEffects: Handles the initialization and playback of various sound effects used in the game, including block hits, bonus events, button presses, and bomb explosions.
- VolumeController: Provides functionality to control the volume of the background music, allowing users to toggle a popup for volume adjustments, mute the music, and set the volume level using a slider.

---
Created a new pauseGame package.

New Class added in pauseGame package:

- PauseHandler: Manages the pausing and resuming of the game, including handling the creation and visibility of the pause menu.
- PauseMenu: Serves as a container for the graphical user interface components related to the pause menu, utilizing the PauseMenuController to manage user interactions.
- PauseMenuController: Handles user interactions with the pause menu buttons.
- PauseMenuView: Defines the visual components of the pause menu, including buttons for resume, restart, and main menu.
- WindowsFocusManager: Monitors the focus of the game window and automatically pauses the game when the window loses focus, preventing unintended gameplay interactions.

---
Created a new mainMenu package:

New Class added in mainMenu package:

- MainMenuController: Manages user interactions with the main menu buttons, initiating actions for starting a new game, navigating to instructions, and accessing high scores.
- MainMenuView: Represents the graphical user interface for the main menu, providing buttons for starting a new game, viewing instructions, and checking high scores, along with associated styling.

---
Created a new levelSelect package:

New Class added in levelSelect package:

- LevelSelectionController: Manages the interactions and logic for the level selection screen, including handling button clicks, initializing the game state for selected levels.
- LevelSelectionView: Represents the graphical user interface for the level selection screen, providing buttons for each playable level, displaying level information, and option to return to the main menu.
- LevelSelectionModel: Stores and manages the selected level for the level selection screen, allowing for communication between the controller and the view components.

---
Created a new instruction package:

New Class added in instruction package:

- InstructionController: Manages user interactions and navigation for the instruction page, handling button click.
- InstructionView: Represents the graphical user interface for the instruction page, displaying game instructions with images and providing a button to return to the main menu.

---
Created a new levelLogic package.

New Class added in levelLogic package:
- RestartLevel: Handles the restart of a specific game level, resetting relevant parameters, and initializing a new game instance for the specified level.

---
Created a new gamePower package.

New Class added in gamePower package:

- Penalty class: Represents a penalty power-up (bomb) in the game, extending the abstract Power class, with specific image URLs.

---
Created a new displayUi package.

New Class added in displayUi package:

- ViewSwitcher: Facilitates the switching of views between various game screens, such as the main menu, high score view, level selection, and instruction pages, managing the associated controllers and scene transitions.

---
Created a new breakMovement package.

New Class added in breakMovement package:

- MouseDragHandler: Handles mouse-drag events to update the position of the break based on the mouse's x-coordinate, ensuring it stays within the scene bounds.

---
New Class added in brickGame package:

- GameState: Serves as a container for storing and managing the state of the brick game (which is initially defined in Main), including information such as the current level, positions of the break and ball, game statistics (score, hearts), block configurations, power-ups, and various flags and parameters that control the game's behavior and interactions.

- By using the GameState class, object is created to encapsulate and manage the state of the brick game, allowing easy access and manipulation of various game parameters by passing the object instance.

### 6. Modified Java Classes

List the Java classes you modified from the provided code base. Describe the changes you made and explain why these modifications were necessary.

Created a new loadSave package.

- Rename LoadSave to ReadFile, and move into the new package loadSave.

Reason: Renaming classes with more descriptive names that reflect their purpose.

- Move saveGame method in Main to a new class SaveGame.
- Move loadGame method in Main to a new class LoadGame.

Reason: Enhances code organization by renaming and relocating the class responsible for reading saved game data to the loadSave package. Improves code structure by segregating the responsibility of saving and loading into separate class.


- Addressed the bug where initially, the game could be saved but not loaded.
- In ReadFile, implemented a statement to check if the save.mdds file exists at the beginning of the game.
- If the save.mdds file exists, the "Load Game" button is displayed on the game scene start page to resume from saved progress.

Reason: Enable player to to resume from saved progress.

- Initially, the saved destroy block count was not initialized to 0. 
- When loading the game, the destroy count remains but the block size is recalculated, leading to errors when proceeding to the next level. 
- To resolve this issue, in saveGame method, the destroy block count is now explicitly set to 0 during the save game process.

- In SaveGame, change the default location for save.mdds to C drive.
- Some computer might not have D drive and will cause error.
----------------------------------------
In the a new levelLogic package:

- Move nextLevel method in Main to a new class NextLevel.
- Move restartGame method in Main to a new class RestartGame.

Reason: To improve code organization and maintain a modular structure, thus splitting methods regarding to the level logic into new package.

-------------------------------------------
In the new gamePower package:

- Move Bonus into new package gamePower.
- Create a new 'Power' class, which is an abstract class that defines a template for creating instances of power-ups in the game. 
- Extract the common functionality into a base class for penalty and bonus.
- The Bonus class has been refactored to extend the abstract Power class, centralizing common power-up functionalities.

Reason: The form template method outlines the steps for creating a power-up, and it delegates the implementation of certain steps to subclasses. This can avoid duplicated codes since bonus and penalty have similiar structure. This also facilitates future additions or modifications to the game elements.

-------------------------------------------
In the new displayUi package:

- Split the method in Score class into different class.

- Move method showWin and showGameOver into EndGameDisplay.
- Move method show into ScoreLabelAnimator.
- Move methos showMessage into MessageLabelAnimator.

Reason: To maintain the Single Responsibility Principle, where each new class has a distinct responsibility.

- The score label may not completely disappear from the screen initially. 
- In method show and showMessage, remove the label from the root after it appears.

Reason: Maintain a tidy and focused game interface, preventing unnecessary visual elements from lingering on the screen.

- Change the button at the win page in showWin method to "Back To Main Menu" button from "Restart" button.
- Removing the contents in the root for a clearer page.
- Add a button "Back To Main Menu" in showGameOver method.

Reason: Players might naturally want to return to the main menu to explore other options instead of starting a new game. 

-------------------------------------------
Created a new ball package.

- Move method initBall in Main to a new class InitBall.
- Move method setPhysicsToBall in Main to new class BallPhysicsHandler.
- Move method resetCollideFlags in Main to new class CollisionFlagsResetter.
- Break the long setPhysicsToBall method into smaller methods and call in setPhysicsToBall, which includes

        updateBallPosition();
        handleTopBottomCollision();
        handleBreakCollision();
        handleLeftRightWallCollision();
        checkWallCollisions();
        checkBlockCollisions();

Reason: Group ball-related functions in a new package, enhances code organization and adheres to the Single Responsibility Principle, promoting maintainability and clarity in the ball-related functionalities.


- Adjusted the collision detection for the ball's interaction with the screen boundary (wall).
- Correct logic error for collideToLeftBlock, set the goRightBall to false.

Reason: To ensure that the entire ball is displayed within the screen, preventing parts of the ball from being outside the visible area.

- Addressed the issue of ball initialization outside the screen.
- In initBall method in InitBall class, set the yBall to a fixed x-position to ensure consistent ball generation.

Reason: Ensures the ball no longer goes out of bounds upon initialization. Prevent the ball fall out of bound.

- Initially the ball will not bounce up when the ball hits the bottom of the screen while falling down in a straight line.
- Add resetCollideFlags after the ball hits the bottom of the screen.

Reason: Ensure the ball collide and move in correct direction.

-------------------------------------------
In the new breakMovement package:

- Move method move in Main to a new class BreakMovementHandler.
- Move method initBreak in Main to a new class InitBreak.

Reason: Grouping break-related functions in a new package enhances code organization and maintainability, promoting a modular and structured design.

- The break may extend beyond the bounds of the screen when moving left and right, causing it to disappear. 
- To address this issue, add statements in method move to ensure the break remains within the specified boundaries.

Reason: Preventing unintended consequences, such as losing control of the break.

-------------------------------------------
Create a new gameAction package.

- Move GameEngine into the package gameAction.
- Move the implementation of OnAction interface in Main to a new class OnAction.
- Split the OnUpdate method in OnAction into smaller methods and move the methods into a new class OnUpdate.

        updateUI();
        updatePowerUpsUI();
        checkGameOver();
        handleGameOver();
        updateGameElements();
        handleBlockHit();
        handleChocoBlock();
        handleStarBlock();
        handleBoomBlock();
        handleBlockType();
        handleCollisionCode();

- Split the OnPhysicsUpdate method in OnAction into smaller methods and move the methods into a new class OnPhysicsUpdate.

        checkDestroyedCount();
        applyBallPhysics();
        updateGoldStatus();
        updatePowerUps();
        isPowerUpHitBreak();
        handlePowerUpHit();

Reason: Improves code organization, reduces clutter in the Main class, and adheres to the principle of single responsibility, enhancing maintainability and readability.

- Extract the common functionalities for chocos and booms (Bonus and Penalty) in OnPhysicsUpdate into a method updatePowerUps.

Reason: Avoid duplicated code.

- Initially, the game used custom threads (updateThread and physicsThread) to handle game updates and physics calculations separately in GameEngine.
- The code changes to utilize the AnimationTimer to create a unified game loop, improving synchronization between update and physics calculations.
- This provides a more standardized and efficient approach to game loop management.

Reason: Avoids concurrent execution of threads, preventing collisions and associated errors in the game.


- When the pause feature is integrated, the game's timer continuing to run even when paused. 

- This result in abnormal behavior for the gold root and bonus, causing extended gold root times, as well as bonuses moving in the opposite direction (towards the top of the screen) instead of falling down.

- To address this issue, add pause and resume method in GameEngine to pause the execution of the timer when the game is paused and resume it when the game is resumed. 

Reason: This ensure proper synchronization and prevent unexpected behavior associated with the gold root and bonus elements.

- Iterator is added for array <Power> in method OnPhysicsUpdate and array <Block> in method OnUpdate.

Reason: Iterators provide a uniform way to iterate over various types of collections in codebase. This promotes consistency and makes the code more predictable and readable.

- Delete unused OnInit in interface OnAction.
- Delete unused setFps in GameEngine.

- In OnUpdate class, add conditions to check for negative score in checkGameOver method because penalty is added and might cause negative score.

-------------------------------------------
Create a new inGameControlKey package.

- Move method handle in Main into a new class KeyEventHandler.
- Move the buttons in Main into a new class GameButtons.
- Move the action event of the buttons above into a new class GameButtonHandler, which act as a controller for the buttons.

Reason: Improves code modularity by separating the handling mechanisms in the game scene, enhancing maintainability, and adhering to the single responsibility principle.

- In GameButtons and GameButtonHandler, add the buttons and event handler for load, back, levelSelect.

- In KeyEventHandler, added new control keys case R for restart level and case P for pause game.
- Remove unused case DOWN.

-------------------------------------------
Create a new block package.

- Move method initBoard in Main to a new class InitBoard.
- Move the methods to get the block size in Block into a new class BlockSizeGetter.
- Extract out the method draw in Block into a new interface BlockFiller, to apply the factory design pattern for block type.
- Interface BlockFiller defines a method applyFill, and concrete implementations for different block types.
- The BlockFillerFactory class is responsible for creating instances of the appropriate BlockFiller based on the block type, which includes ChocoBlockFiller, HeartBlockFiller, BoomBlockFiller, StarBlockFiller, and NormalBlockFiller.

Reason: By centralizing the creation logic in the factory, can easily add new block types without modifying existing code. This makes the system adaptable to changes and additions in the future.

- Addressed the bug where initially, the ball failed to hit and destroy blocks sometimes as only the central point of the ball's circumference triggered block destruction.
- Modified the block destruction mechanism in method checkHitToBlock in Block class to consider the entire ball radius for collision detection.

Reason: Ensure that block can be destroyed when any part of the ball, within its radius, hits the block. Ensures a more consistent gameplay experience.

- Generate blocks for levels 19 and 20 in Block class, each featuring 10 rows of blocks. 
- Level 19 introduces bonus elements, while level 20 introduces boom elements.
- Generate boom block starting from level 5 which will generate bomb to cause penalty while receive with break.

Reason: Adding strategic challenges for players to navigate through.

- Move BlockSerializable from brickGame package to block package.
- Rename variable j to column to better represent the variable's usage.

-------------------------------------------
In the brickGame package: 

- Move the game start scene in Main to a new class GameInitializer.
- Make the Main class simple with only the start stage and some getter methods.

- Break the large method into smaller methods.

        resetGameForMainMenu();
        clearRootAndPauseHandler();
        initializeSoundEffects();
        handleGameSetup();
        handleGameWin();
        setupGameSceneAndKeyEvents();
        setupGameButtonsAndHandlers();

- Group repeated code into methods.

        initializeRootAndLabels();
        setGameElementsVisible();
        setButtonInvisible();
        restartEngine();

Reason: To reduce redundancy and promoting a more modular structure.

- In GameInitializer, change the last level to 19, which when the level=20, then only the method handleGameWin will be called.
- Add conditions for restart level at level 1, which the game play will start immediately. (because restart level at level 1 initially will start with the game scene start page)
### 7. Unexpected Problems

---
#### Unfamiliar with JavaFX
- Encountering unfamiliarity with JavaFX presented a challenge during the assignment. 
- Took proactive steps to learn more about JavaFX by searching online resources and seeking additional information through interactions with ChatGPT. 
- This approach demonstrates a resourceful and adaptive strategy to overcome unfamiliar technologies and gain the necessary knowledge for successful completion of the assignment.

---
#### Time Management Issue
- Facing challenges with too many assignments and a lack of time management.
- Breaking down big tasks into smaller parts and allocating time for them each day.
- This approach help in maintaining a more organized and manageable workload, leading to improved time management and reduced stress.

