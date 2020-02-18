#!/bin/env bash

# This is script to launch the server in development mode.

# Launch docker daemon if it isn't running

systemctl is-active docker > /dev/null

if [[ $? -ne 0 ]]; then
	echo "The docker daemon is not running"
	sudo systemctl start docker || exit 1
fi


# Runs a mongo container
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
