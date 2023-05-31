# Chain of Stones Game

Description
-----------
This project is an interactive implementation of the game Chain of Stones using JavaFX.
It provides a graphical user interface where two players can compete against each other on a 5x4 game board, 
strategically placing red and blue stones to form an unbroken chain of three stones horizontally, vertically, or diagonally.

![Screenshot 2023-05-31 140601](https://github.com/INBPA0420L/homework-project-MoShabayk/assets/56825442/db5d8b7e-f323-4509-af51-ba07eaf7dd30)

Game Rules
----------

|     |     |     |     |
|:---:|:---:|:---:|:---:|
|  B  |  R  |  B  |  R  |
|     |     |     |     |
|     |     |     |     |
|     |     |     |     |
|  R  |  B  |  R  |  B  |

- The game is played by two players.
- The game board consists of 5 rows and 4 columns.
- The game is played with 4 blue stones and 4 red stones.
- Initially, the stones are arranged on the board as follows:
    first row:  blue, red, blue, red
    last row:   red, blue, red, blue
- Players move in turn, the first player plays with the red stones, the other with the blue stones.
- In a move, a player must move one of his or her stones by one square to an empty square, either horizontally or vertically.
- The winner is the player who first forms an unbroken chain of three stones of his or her color horizontally, vertically, or diagonally.


Game Features
-------------
- Player Names: The game asks the two players to enter their names before starting.
- Game History: The game stores the result of the games in a JSON file.
- High Score Table: The game displays a high score table in which the top 5 players with the most wins are displayed.
- Game Statistics: The game displays the number of turns made by the players during the game.

Data Storage
------------
The game data is stored in a JSON file. Each game record is stored as a JSON object with the following fields:

- Date and Time: The date and time when the game was started.
- Player Names: The names of the players.
- Number of Turns: The number of turns made by the players during the game.
- Winner: The name of the winner. 

The JSON file is updated with each new game record, and retrieve the data when needed.