#!/bin/bash
echo "Building separate-process version..."
mvn clean package -P separateprocess

echo "Starting server..."
java -jar target/player-message-exchange-1.0-SNAPSHOT-server.jar &
SERVER_PID=$!
sleep 2  # Wait for server to start

echo "Starting players..."
java -jar target/player-message-exchange-1.0-SNAPSHOT-player.jar Player1 true &
java -jar target/player-message-exchange-1.0-SNAPSHOT-player.jar Player2 false &

# Wait for players to complete (simple approach)
wait $SERVER_PID
echo "Separate process execution completed."