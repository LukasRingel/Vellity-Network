# Service Worker

## Description

This microservice holds all worker logic for the usercache components.

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- worker-common ([README.md](../../components/worker-common/README.md))

## Configuration
```text
# Redis
REDIS_HOST=database.vellity.net
REDIS_PORT=7000
REDIS_PASSWORD=secret

# MySQL
MYSQL_HOST=database.vellity.net
MYSQL_PORT=3306
MYSQL_DATABASE=access
MYSQL_USER=access
MYSQL_PASSWORD=secret

# Explorer
EXPLORER_HOST=api.vellity.net/explorer
EXPLORER_AUTH_KEY=secret
```

## Database Setup

### table creation

```sql
```