# RPG JAVA

A modular turn-based RPG built in Java to practice object-oriented programming, inheritance, polymorphism, enums, records, and separation of concerns.

The game currently runs in the command line and is structured so the core game logic can later be adapted into a Spring Boot REST API with a React frontend.

## Features

- Choose between Warrior, Mage, and Rogue classes
- Unique special ability for each player class
- Turn-based combat
- Basic attacks and defensive stats
- Health potions
- Input validation
- Multiple enemies and a final boss
- Reward choices between battles
- Structured combat results using Java records
- Modular architecture designed for future API integration

## Character Classes

### Warrior

- Highest health
- Highest defense
- Uses Power Strike for increased damage

### Mage

- Lower health and defense
- Highest base attack
- Uses Fireball for additional damage

### Rogue

- Balanced stats
- Uses Shadow Strike
- Has a chance to land a critical hit

## Game Flow

The player selects a class and fights through a short sequence of enemies:

```text
Goblin
   ↓
Skeleton
   ↓
Dragon