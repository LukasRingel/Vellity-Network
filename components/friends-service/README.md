# Service Friends

## Description

This microservice is responsible for player friendships

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- friends-common ([README.md](../../components/friends-common/README.md))

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
create table friends_users_friends
(
    id          int auto_increment                  primary key,
    context     int                                 not null,
    player      char(36)                            not null,
    friend      char(36)                            not null,
    state       int                                 not null,
    activeUntil timestamp                           not null,
    changedAt   timestamp default current_timestamp not null,
    createdAt   timestamp default current_timestamp not null
);
create table friends_users_requests
(
    id          int auto_increment                  primary key,
    context     int                                 not null,
    player      char(36)                            not null,
    target      char(36)                            not null,
    activeUntil timestamp                           not null,
    createdAt   timestamp default current_timestamp not null
);
create table friends_users_blocked
(
    id          int auto_increment                  primary key,
    context     int                                 not null,
    player      char(36)                            not null,
    target      char(36)                            not null,
    activeUntil timestamp                           not null,
    createdAt   timestamp default current_timestamp not null
);
create table friends_users_settings
(
    id      int auto_increment primary key,
    context int                not null,
    player  char(36)           not null,
    setting int                not null,
    status  int                not null,
    unique (context, player, setting)
);
```