
# Brick Ball Game

## 1. Compilation Instructions: 

Provide a clear, step-by-step guide on how to compile the code to produce the application. Include any dependencies or special settings required.

## 2. Implemented and Working Properly
### Additional Features

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
The game detects frame loss or window focus loss.
Automatically pauses the game when frame loss occurs.

User-Friendly Interface:
When the player hovers over each option in the pause menu, a visual hover effect is triggered.
### b. Sound Effects

Sound effects will be triggers when the condition occurs.

- Ball Hit Block: "Tok" triggers when the ball collides with a block.
- Bonus: "Ding" triggers when the player gains a bonus with the break.
- Penalty (Boom): "Boom" triggers when a receive the boom (penalty) with the break.
- Button Pressed: "Clak" triggers when player press on any buttons in the game.

### c. Background Music (BGM)

BGM starts playing when the player first starts the game.
The music continues to play continuously throughout the entire game.

Looping Feature:
BGM automatically loops when it reaches the end, providing a seamless and uninterrupted musical experience.

Volume Control:
- BGM starts at a default volume level
- Player can press "M" key to open the volume control menu.
- A slider in the volume control menu allows the player to adjust the loudness of the BGM.
- Checkbox in the volume control menu enables the player to mute or unmute the BGM.
- Offers flexibility for players who may want to enjoy the game with or without music.
### d. Main Menu Page

When the player first runs the game, a main menu page is displayed, offering several options.

- Start: Selecting "Start" initiates the game, transitioning from the main menu to the gameplay environment.

- Instructions: Choosing "Instructions" provides information on key controls and game mechanics.

- Highscore: Clicking "Highscore" displays a page showcasing the highest scores achieved in the game.
## e. Instruction Page

When the player selects "Instruction" in the main menu, the player will be directed to the Instruction Page.

Key Controls Display:
Clearly presents keyboard and mouse controls required for different in-game mechanisms.
Provides players with a comprehensive understanding of how to interact with the game.
Utilizes icons to accompany textual descriptions of controls.

Back to Main Menu:
Includes a button "Back to Main Menu" button on the instruction page after reviewing the instructions.
### f. High Score Implementation

When the player loses (runs out of hearts) or wins the game, a dialog box pops up if the player achieves a highscore within the top 3.

The dialog box prompts the player to enter their name to associate with the highscore.

After entering the name, an alert box informs the player that they can view the highscore in the Highscore Page.

If the player presses cancel or doesn't input a name, the highscore is not saved.

The player's name and score are saved in a highscore.mdds file.
Allows the game to retain highscores even if the player closes and reopens the game.

High Score Page:
Accessible from the main menu, the highscore page displays the top 3 names with their corresponding highscores.


No Record: If no highscores exist, the highscore page displays a message indicating "No Record."





### g. Game Scene Start Page

Accessed when the player selects "Start" in the main menu. Displays buttons for different game options within the game scene.

#### New Game (Included in default Implementation): Allows the player to start a new game from level 1.

Back to Main Menu:
Provides an option to return to the main menu from the game scene.

Saved Process Check:
If there is a saved game process, displays two additional buttons.

Level Selection:
Permits the player to select a level equal to or below the saved level.
Default settings with a score of 0 and 3 hearts to maintain fairness for highscore purposes.

Back to Main Menu (Level Selection):
Offers the option to go back to the main menu from the level selection screen.

Load Game:
Allows the player to load the saved game.
Resumes the game from the saved progress.
### h. Main Menu Button

Added Back To Main Menu Button in different page to direct user back to main menu.
### i. Mouse Drag Handler

Added a mouse control, at which the user can drag and move the break with mouse.
### j. Restart Level

Player can restart the current level by pressing "R" or from the Pause Menu.
