# Service Config

## Description

This microservice is responsible for configurations and feature flags.

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- config-common ([README.md](../../components/config-common/README.md))

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

# Config Repository
REPOSITORY_HOST=https://git.vellity.net/configurations.git
REPOSITORY_USERNAME=git
REPOSITORY_PASSWORD=secret
REPOSITORY_BRANCH=main
REPOSITORY_LOCAL_PATH=configs/
```

## Database Setup

### table creation

```sql
create table config_flags
(
    id         int auto_increment
        primary key,
    context    int                                 not null,
    name       varchar(36)                                 not null,
    enabled    bit       default 0                 not null,
    activeUntil timestamp default current_timestamp not null
);
```