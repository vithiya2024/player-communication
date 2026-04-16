# Player Communication Game

This project is a Java-based game that supports both single-process and multi-process modes. It uses Maven for build management.

## Features
- Single-process mode: Runs the game in a single Java process.
- Multi-process mode: Runs the game with a server and client communicating over a network.

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Bash shell (for running the `StartGame.sh` script)

---

## Project layout
```css
player-communication/
├── pom.xml
├── StartGame.sh
├── README.md
└── src
    └── main
       └── java
           └── com
               └── example
                   └── playergame
                       |   └── App.java
                       ├── common
                       |   └── Message.java                    
                       ├── single_process
                       |   ├── Player.java
                       |   └── GameController.java
                       └── multi_process
                           ├── PlayerServer.java
                           └── PlayerClient.java
    
```
## SINGLE_PROCESS
This runs two Player instances within the same JVM using an in-memory channel.

### How to build
```bash
mvn clean package
```

### How to run
```bash
./StartGame.sh 
# or
java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.playergame.App
   
```

### What it does
- Initiator sends first message.
- Responder replies with received content + its counter.
- Stops when initiator has received and sent maxMessages (default 10).


----------------------------------------------------------------------------------------------------------

## MULTI_PROCESS
This runs two processes (separate JVMs) communicating over TCP sockets.

## How to build
```bash
mvn clean package
```

### How to run
Run the bellow command it internally starts server and client processes.
```bash
./StartGame.sh multi
```
### What it does
- Client connects to Server.
- Client (initiator) sends first message, they exchange messages.
- Stops when initiator has received maxMessages replies (default 10).


  
