KingsSGF V2.0
=========

KingsSGF is a magical game.

__NOTE: WE SUPPORT IN-GAME CHAT :)__

  - Super fun!
  - Much game!
  - Very quirky!
  
### Features Implemented
  * Loading from Predefined Board or a Random Board
  * 2, 3 and 4 Player Game Support (with reduced layout for 2-3 player)
  * Gold Collection Phase
  * Recruiting Heroes Phase
  * Recruiting Things Phase
  * Random Events (Defection and Good Harvest)
  * Movement Phase
  * Combat Phase
  * Special Powers Phase
  * MUCH MORE :)
  * CHAT FUNCTIONALITY
  * /commands
  * /nick command

### Current Bugs

For some reason this game has a lot of issues. For the moment the following:
  * Join Game breaks if you join the game twice (it won't stop you)
  * In PVP Battle, other players do not see what the other player rolled (but they do know if they got hit or not)
  * (POSSIBLY FIXED) Networking struggling with non-blocking reads
    * Header/Body style ByteBuffer used should resolve this
    * Etc....but very smooth gameplay overall.

### Type of Game
This is a client/server based game where there is a hosting server (outside of the player). A server is responsible for hosting one game from start to finish. Both ends coded in Java.

Mobile Observer client is possible (but not functional for the moment).

### Languages and Resources
KingsSGF was coded using the following technologies:
  * Java
    * Both the client and the server portion were coded completely in Java
  * JavaFX
    * For the UI (windows, images, drag logic)
      * Views are created visually using JavaFX's SceneBuilder application and loaded into the game via FXML Injection
  * Java's NIO Non-Blocking Input Output Library
    * For the server/client TCP communication
    * Communication is done over the wire using ByteBuffers
  * Jackson JSON Library
    * All data is serialized over the networking using Jackson to serialize/deserialize objects and instructions into JSON.
      * This makes it possible to easily incorporate a mobile client "monitor" in the future (or web based monitor)
    * The use of Jackson facilitated easy use of polymorphic deserialization, transfering an object across the wire that gets casted to the baseclass while retaining its polymorphic properties if it was a subclass

### How to Compile
  1. Import the Project into Eclipse (File -> Import Project into Workspace)
  2. File -> Export -> Runnable JAR
  3. Or alternatively, RunAs from within Eclipse
  
### How To Run
Server:

  1. Open JAR or Start uiStart.java.
  2. Select port (host address does not matter)
  3. Select Number of players, and Layout Mode (Random, Minimal, Average, Superior, Outstanding)
  4. Click Host Button
  
Client:

  1. Open JAR or Start uiStart.java.
  2. Input port and address of server (defaults to localhost)
  3. Click Connect
  4. Once game window pops up, click File -> Join Game
  5. Once all players joined (game will tell you), do roll and then
  5.1. At this point in the chatbox you can type /nickNAME where NAME is a name of your choice :)
  6. File -> Start Game once it says. (ONLY ONE PLAYER WILL NEED TO START THE GAME)
  7. PLAY!

### Version
Demo2.0

### NO HARDCODE GUARANTEE
This code is certified unhardcoded. No game logic despite Demo1 methods such as initializing the game board and giving the players the proper things is hard coded. All game logic is dynamic and should be functional ;).

### Coded by
[@prashker](http://prashker.net)

### Documentation
[@ghassanansari](https://github.com/ghassanansari)

[@faisalbajnead](https://github.com/faisalbajnead)

    