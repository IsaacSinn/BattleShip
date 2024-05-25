# Battleship Game

## Overview
Battleship is a two-player game where each player controls a fleet of ships on a discrete board with the same dimensions. Players place their ships secretly, ensuring they touch but do not overlap. After all ships are placed, players take turns guessing coordinates on their opponent's board. The goal is to sink the opponent's entire fleet by hitting all the squares occupied by their ships.

### Gameplay
1. **Board Setup**:
   - Each player places their ships on their board in secret.
   - Ships can touch but cannot overlap.
   - Once placed, ships cannot be moved.

2. **Turn-Based Guessing**:
   - Players take turns guessing coordinates on the opponent's board.
   - The opponent provides feedback based on the guess:
     - **MISS**: No ship at the guessed coordinate.
     - **HIT**: A ship is at the guessed coordinate but is not fully sunk.
     - **SUNK**: The guessed coordinate sinks a ship, and the player is informed of the ship type.

3. **Winning**:
   - The player who sinks the entire fleet of the opponent wins the game.

## Running the Game

### Windows
To compile and run the game from the `cs440` directory, use the following commands:

```bash
javac -cp "./lib/*;." @battleship.srcs
java -cp "./lib/*;." edu.bu.battleship.Main
```

## Command line options

### General Usage

```bash
java -cp "./lib/*;." edu.bu.battleship.Main [options]
```
Add -h or --help to see the help message for all command line options.

### Player Agent 

`--p1Agent`: Specifies the fully-qualified path to the Java class of the agent for player 1.
Example Usage: 
```bash
--p1Agent src.pas.battleship.agents.ProbabilisticAgent
```
Defaults to edu.bu.battleship.agents.RandomAgent if not specified.

### Difficulty

`-d` or `--difficulty`: Specifies the difficulty of the game. Options are `EASY`, `MEDIUM`, and `HARD`.

`EASY`: 10x10 map with
-  1 Aircraft Carrier (5 squares)
-  1 Battleship (4 squares)
-  1 Destroyer (3 squares)
-  1 Submarine (3 squares)
-  1 Patrol Boat (2 squares)

`MEDIUM`: 20x20 map with
- 1 Aircraft Carrier (5 squares)
- 1 Battleship (4 squares)
- 2 Destroyers (3 squares each)
- 2 Submarines (3 squares each)
- 3 Patrol Boats (2 squares each)

`HARD`: 30x30 map with
- 2 Aircraft Carriers (5 squares each)
- 3 Battleships (4 squares each)
- 4 Destroyers (3 squares each)
- 4 Submarines (3 squares each)
- 5 Patrol Boats (2 squares each)

Example:
```bash
-d MEDIUM
```

### Silent mode
`-s` or `--silent`: Runs the game without a rendering window. Useful for faster execution


### Thinking Time
`-t` or `--thinkingTimeInMS`: Specifies the total thinking time for each agent in milliseconds. If an agent's time drops to zero, they lose the game.
Defaults to `480000` ms (8 minutes)
