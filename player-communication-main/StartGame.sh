#!/bin/bash
# StartGame.sh - build and run the game (single or multi-process)

mvn clean package -q

if [ "$1" == "multi" ]; then
  echo "Starting multi-process mode..."
  java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.playergame.multi_process.PlayerServer 5000 &
  SERVER_PID=$!
  sleep 1
  java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.playergame.multi_process.PlayerClient localhost 5000
  wait $SERVER_PID
else
  echo "Starting single-process mode..."
  java -cp target/player-communication-1.0-SNAPSHOT.jar com.example.playergame.App
fi

