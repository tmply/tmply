#!/bin/bash

docker run --rm \
	--env POSTGRES_USER=tmply \
	--env POSTGRES_PASSWORD=asdfasdf \
	--env POSTGRES_DB=tmply \
	-p 5432:5432 \
	postgres:9.6
