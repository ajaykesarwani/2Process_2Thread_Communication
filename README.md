# Player Message Exchange

This project implements a simple message exchange system between two players. It includes two modes:

1. **Same Process Mode**: Both players communicate within the same process using threads.
2. **Separate Process Mode**: Each player runs in a separate process and communicates through socket programming.

## Project Structure

- `src/main/java/com/example/sameprocess/`
  - `Player.java`: Represents a player who sends and receives messages.
  - `MessageExchange.java`: Manages message exchanges between players.
  - `Main.java`: Main class to run the same process communication using threads.

- `src/main/java/com/example/separateprocess/`
  - `MessageExchangeServer.java`: A socket server that facilitates message exchange between two separate clients (players).
  - `PlayerApp.java`: Represents a player client that connects to the server and exchanges messages.
  - `Main.java`: Launches the server and the clients in separate processes.

- `sameprocess.sh`: Shell script to compile and run the thread-based message exchange.
- `separateprocess.sh`: Shell script to compile and run the socket-based message exchange.
- `pom.xml`: Maven configuration file.

## How to Run the Project

### Prerequisites

- Java 8 or above
- Maven
- Unix-like terminal (for `.sh` scripts)

### Steps

1. **Unzip the project**.
2. **Open a terminal and navigate to the root project directory**.

---

### Run in Same Process Mode (Thread-based):

```bash
./sameprocess.sh
```
This will compile the code and run the player interaction using threads in a single process.


### Run in Separate Process Mode (Socket-based):
```bash
./separateprocess.sh
```
This will compile the code and launch the server and two player clients in separate processes. The players will exchange messages through the server.

## Sample Output: for both the cases

This output demonstrates the message exchange between Player1 and Player2.
### Message Exchange Trace:

- Player1 sent: Hello from Player1
- Player2 received: Hello from Player1
- Player2 sent: Hello from Player1 - response #1
- Player1 received: Hello from Player1 - response #1
- Player1 sent: Hello from Player1 - response #1 - response #2
- 
-


- In this simulation, Player1 and Player2 communicate using inter-thread messaging.

- Player1 initiates the conversation by sending "Hello from Player1" to Player2.

- Player2 receives the message, appends " - response #1" to it, and sends it back.

- Player1 then appends its own " - response #2" again to the received message and sends it to Player2.

This message exchange continues, each appending a response suffix, until 10 total message exchanges are completed by each player.
