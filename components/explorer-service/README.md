# Service Explorer

## Description

This microservice is responsible for providing information about the other microservices.
It is also responsible for the registration of the microservices.

## Decisions

- 15.06.2023: We save the information about the microservices in a redis database to maintain the service as stateless
  as possible.
- 16.06.2023: Lets keep the services in memory and save to redis in future versions if the load is too high.

## Endpoints

### GET /status

Returns the status of the service.

### GET /find?type={type}

Provides the registered services for the given type {type}.

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- explorer-common ([README.md](../../components/explorer-common/README.md))

## Configuration
```text
# Redis
REDIS_HOST=database.vellity.net
REDIS_PORT=7000
REDIS_PASSWORD=secret

# Http Server
HTTP_SERVER_PORT=8080
HTTP_SERVER_API_KEY=secret
```