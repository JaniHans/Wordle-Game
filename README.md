# koodWordle

A command-line implementation of the popular word-guessing game Wordle, written in Java.

## Description

koodWordle is a terminal-based Wordle game where players have 6 attempts to guess a 5-letter word. The game provides feedback after each guess and tracks player statistics.

## Features

- 6 attempts to guess the correct 5-letter word
- Color-coded feedback for each guess
- Word validation against a dictionary
- Letter tracking to show remaining available letters
- User statistics tracking (wins, losses, attempts)
- Persistent stats stored in CSV format

## How to Run

1. Compile the Java files:
```bash
javac WordleGame.java game/*.java io/*.java model/*.java
```

2. Run the game with a word index:
```bash
java WordleGame <word_index>
```

Example:
```bash
java WordleGame 42
```

## How to Play

1. Enter your username when prompted
2. Guess a 5-letter word (lowercase only)
3. Receive feedback on your guess:
   - Letters in the correct position
   - Letters in the word but wrong position
   - Letters not in the word
4. Use the remaining letters guide to help narrow down options
5. Win by guessing the word within 6 attempts!

## Project Structure

```
koodWordle/
├── WordleGame.java          # Main entry point
├── game/
│   ├── GameLogic.java       # Core game logic
│   ├── FeedBackGenerator.java  # Feedback system
│   └── LetterTracker.java   # Letter tracking
├── io/
│   ├── WordLoader.java      # Word list loader
│   └── StatsManager.java    # Statistics management
├── model/
│   └── UserGame.java        # User game data model
├── wordle-words.txt         # Valid word dictionary
└── stats.csv                # Player statistics
```

## Requirements

- Java 8 or higher
- `wordle-words.txt` file in the project directory

## Cleaning Build Files

To remove compiled `.class` files before committing to Git:

**Windows (PowerShell):**
```powershell
Remove-Item -Path "*.class" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "game\*.class" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "io\*.class" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "model\*.class" -Force -ErrorAction SilentlyContinue
Remove-Item -Path "out" -Recurse -Force -ErrorAction SilentlyContinue
```

**Linux/Mac:**
```bash
find . -name "*.class" -type f -delete
rm -rf out/
```

The `.gitignore` file is configured to automatically exclude `.class` files and build directories from Git.

