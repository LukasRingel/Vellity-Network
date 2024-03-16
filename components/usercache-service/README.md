# Service Usercache

## Description

This microservice is responsible for caching user profiles

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- usercache-common ([README.md](../../components/usercache-common/README.md))

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
create table usercache_users_names
(
    id   int auto_increment primary key,
    uuid char(36)    not null    unique,
    name varchar(16) not null
);
create table usercache_users_names_history
(
    id              int auto_increment
        primary key,
    player          char(36)                            not null,
    name            varchar(16)                         not null,
    updatedAt       timestamp default current_timestamp not null
);
create table usercache_users_textures
(
    id        int auto_increment primary key,
    uuid      char(36) not null unique,
    signature text     not null,
    value     text     not null
);
create table usercache_users_textures_history
(
    id        int auto_increment
        primary key,
    player    char(36)                            not null,
    signature text                                not null,
    value     text                                not null,
    changedAt timestamp default current_timestamp not null
);
create table usercache_users_logins
(
    id      int auto_increment primary key,
    context int                                 not null,
    uuid    char(36)                            not null,
    address text                                not null,
    date    timestamp default current_timestamp not null
);
create table usercache_users_logouts
(
    id      int auto_increment primary key,
    context int                                   not null,
    uuid    char(36)                              not null,
    login   int                                   not null,
    date    timestamp default current_timestamp() not null
);
create table usercache_users_messages
(
    id        int auto_increment
        primary key,
    player    char(36)                              not null,
    context   int                                   not null,
    message   text                                  not null,
    status    int                                   not null,
    createdAt timestamp default current_timestamp() not null
);
create index usercache_users_messages_player_index
    on usercache_users_messages (player);
create table usercache_users_notifications
(
    id           int auto_increment
        primary key,
    player       char(36)                              not null,
    context      int                                   not null,
    notification int                                   not null,
    status       bit                                   not null,
    activeUntil  timestamp                             not null,
    createdAt    timestamp default current_timestamp() not null,
    updatedAt    timestamp default current_timestamp() null on update current_timestamp(),
    constraint usercache_users_notifications_player_index
        unique (player, context, notification)
);
```