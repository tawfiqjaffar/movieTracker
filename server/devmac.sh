#!/usr/bin/env bash

# This is the script to launch the server on a macOS environment

if (! docker stats --no-stream ); then
  open /Applications/Docker.app
while (! docker stats --no-stream ); do
  echo "Waiting for Docker to launch..."
  sleep 1
done
fi


container_name="movieApp_dev_mongo"

id=$(docker ps --filter "name=$container_name" --format "{{.ID}}" || exit 1)

if [[ $id = "" ]]; then
	id=$(docker ps -a --filter "name=$container_name" --format "{{.ID}}" || exit 1)

	if [[ $id = "" ]]; then
		docker run -p 27017:27017 --name $container_name -d mongo
	else
		docker start $id
	fi
fi
npx nodemon src/app.js --exec 'npm run lint && node'
