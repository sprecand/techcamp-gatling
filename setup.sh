#!/bin/bash

# Define the usage message
usage() {
    echo "Usage: $0 {start|stop|gatling|local|logs}"
    echo "  start  : Runs 'docker-compose up' to start all services (db and gatling-backend)."
    echo "  stop   : Runs 'docker-compose down --remove-orphans' to stop all services and remove orphans."
    echo "  gatling: Runs Gatling to test the service under pressure."
    echo "  local  : Runs only the 'db' service and starting the 'gatling-backend' locally."
    echo "  logs   : Get logs from the gatling-backend"
    exit 1
}

# Check the number of arguments
if [ "$#" -ne 1 ]; then
    usage
fi

# Execute based on the argument
case "$1" in
    start)
        echo "Starting Docker Compose services (db and gatling-backend)..."
        docker-compose down --remove-orphans
        docker-compose up -d
        ;;
    stop)
        echo "Stopping Docker Compose services and removing orphans..."
        docker-compose down --remove-orphans
        ;;
    gatling)
        echo "Starting Gatling"
        cd gatling
        mvn gatling:test
        ;;
    local)
        echo "Starting only the 'db' service..."
        docker-compose down --remove-orphans
        docker-compose up -d db
        cd gatling-backend
        mvn spring-boot:run
        ;;
    logs)
        docker-compose logs gatling-backend
        ;;
    *)
        echo "Invalid parameter."
        usage
        ;;
esac
