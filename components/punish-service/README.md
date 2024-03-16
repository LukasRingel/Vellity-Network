# Service Punish

## Description

This microservice is responsible for player punishments

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- punish-common ([README.md](../../components/punish-common/README.md))

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
create table punish_reasons
(
    id             int auto_increment
        primary key,
    context        int         not null,
    name           varchar(48) not null,
    punishmentType int         not null,
    activeUntil    timestamp   not null,
    constraint punish_reasons_pk2
        unique (context, name, punishmentType)
);
create table punish_reasons_durations
(
    id          int auto_increment
        primary key,
    reason      int       not null,
    timeunit    int       not null,
    amount      int       not null,
    multiplier  double    not null,
    activeUntil timestamp not null
);
create table punish_users_punishments
(
    id          int auto_increment
        primary key,
    context     int       not null,
    type        int       not null,
    player      char(36)  not null,
    reason      int       not null,
    activeUntil timestamp not null,
    createdAt   timestamp default current_timestamp() not null
);
create table punish_users_punishments_actions
(
    id          int auto_increment
        primary key,
    punishment  int                                   not null,
    type        int                                   not null,
    actor       char(36)                              not null,
    beforeValue text                                  null,
    afterValue  text                                  null,
    createdAt   timestamp default current_timestamp() not null
);
create table punish_users_punishments_proofs
(
    id          int auto_increment
        primary key,
    punishment  int                                   not null,
    type        int                                   not null,
    issuer      char(36)                              not null,
    value       text                                  not null,
    createdAt   timestamp default current_timestamp() not null,
    activeUntil timestamp                             not null
);
```