#!/bin/bash
echo "Building and running same-process version..."
mvn clean package -P sameprocess
echo "Starting same-process message exchange..."
java -jar target/player-message-exchange-1.0-SNAPSHOT.jar
echo "Sameprocess execution completed."