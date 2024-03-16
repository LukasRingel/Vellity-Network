# Service Guardian

## Description

This microservice is responsible for player punishments and reporting

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- guardian-common ([README.md](../../components/guardian-common/README.md))

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
create table guardian_chatlogs
(
    id          int auto_increment
        primary key,
    context     int                                   not null,
    creator     char(36)                              not null,
    target      char(36)                              not null,
    createdAt   timestamp default current_timestamp() not null,
    activeUntil timestamp                             not null
);
create table guardian_chatlogs_messages
(
    id      int auto_increment
        primary key,
    chatlog int not null,
    message int not null
);
create table guardian_reports
(
    id          int auto_increment
        primary key,
    context     int                                   not null,
    target      char(36)                              not null,
    state       int                                   not null,
    reason      int                                   not null,
    creator     char(36)                              not null,
    activeUntil timestamp                             not null,
    createdAt   timestamp default current_timestamp() not null
);
create table guardian_reports_actions
(
    id        int auto_increment
        primary key,
    reportId  int                                   not null,
    player    char(36)                              not null,
    action    int                                   not null,
    createdAt timestamp default current_timestamp() null
);
create table guardian_reports_reasons
(
    id          int auto_increment
        primary key,
    context     int         not null,
    name        varchar(24) not null,
    activeUntil timestamp   not null,
    constraint context
        unique (context, name)
);
```