# Codex Naturalis: Software Engineering Final Project

**Codex Naturalis** Board Game is the final project of **Software Engineering** course, part of Computer Science and Engineering Bachelor's Degree at Politecnico di Milano (2023/2024).
This project involves developing a Java Application of the board game Codex Natualis, originally created by [_Cranio Creations_](https://www.craniocreations.it/prodotto/codex-naturalis).


## Group Members
* [Lorenzo Valentini](https://github.com/lorenzotini)
* [Andrea Vicedomini](https://github.com/bicix-72)
* [Toni Wang](https://github.com/wangteyi)
* [Lucrezia Vaccaro](https://github.com/lucreziavaccaro)



## Basic Functionalities
| Functionality  | Implemented |
|----------------|:-----------:|
| Full rules     |      ✅       |
| RMI           |      ✅      |
|Socket         |      ✅      |
|TUI            |      ✅      |
|GUI            |      ✅      |

## Advanced Functionalities
|Funcionality  | Implemented |
|--------------|:------------|
| Chat         | ✅           |
| Multiple Games| ✅            |
|Resilience to client disconnection| ✅          |
| Persistence of server crash| ⛔         |

## Code Coverage


## JAR Compilation
Two different JAR files must be created, one for the server and the other for the client.
For the server, build a jar using ServerApp.java as the main class. For the client, build a jar using MainClient.java as the main class.
After that the two jars can be executed.

## JAR Execution
**Server**

In order to execute the server application, go to the folder where the server jar file was built and run the command below in the terminal. No more arguments are required, as the port is predefined. 
```shell
java -jar GC27.jar
```

**Client**

Do the same for the client application, but with some additional arguments if needed:
the default configuration for the client sets RMI as client-server communication type, TUI as User Interface and _localhost_ as the IP address.
For connecting to a server on the same network, write its private IP Address as first argument after the jar file name.
```shell
java -jar GC27.jar 
```

Two more arguments can be specified to the client jar, their order does not affect the execution:
*  _--rmi_ or _--socket_  to specify the client-server communication type
* _--tui_ or _--gui_ to specify the User Interface type

An example of a complete configuration specifying a custom server IP address, GUI, and socket communication:
```shell
java -jar GC27.jar 192.168.56.1 --gui --socket
```

Executing the client jar followed by _--help_ or _--h_ parameter displays all configurations.

## Playing the game

**Tui**

After the login process, the game can starts when all the players are connected.
When creating a new game, an ID is assigned to the game: use it to connect to the same game from different clients.
Once the game is started, a list of commands is displayed. To show it again during the game, type _help_.
In general, once called a command the user must follow the instructions displayed on the screen.
Now some tips:
* the manuscript is where the player can play cards: a "@" is used to suggest a valid card placement;
* the "■" as corner symbol represents a non-coverable corner;
* when drawing a card (type _draw_ as command), always specify the full command, e.g. _"res true 1"_, that is to say an index is required (it will be ignored in this case since _true_ was specified as _[fromDeck]_ parameter) at the end even though the card requested does not come from the face-up cards;

**Gui**

When creating a new game, an ID is assigned to the game: use it to connect to the same game from different clients.
Once the game is started, the user can interact with the GUI by clicking on the buttons and following the instructions displayed on the screen.
Now some tips: 
* to play a card, drag it from the hand to the manuscript (grey areas indicate where the card can be placed);
* to play a card upside down, first click on it and then drag it to the manuscript;
* to draw a card, click on the deck or on the face-up cards;

## Clarifications
* The project is developed in Java 21, so it is recommended to use the same version to avoid compatibility issues.
* The correct behaviour of the application is not guaranteed on Linux and macOS machines, since it was developed only on Windows.