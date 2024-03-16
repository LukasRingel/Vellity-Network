# Service Babbel

## Description

This microservice is responsible for player balances and currency management.

## Decisions

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- economy-common ([README.md](../../components/economy-common/README.md))

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
create table economy_users_balance
(
    id       int auto_increment primary key,
    playerId char(36)           not null,
    balance  double default 0   not null,
    currency int                not null,
    unique (playerId, currency)
);
create table economy_users_transactions
(
    id        int auto_increment                  primary key,
    playerId  char(36)                            not null,
    currency  int                                 not null,
    amount    double                              not null,
    action    int                                 not null,
    createdAt timestamp default current_timestamp not null
);
create table economy_users_booster
(
    id          int auto_increment                 primary key,
    playerId    char(36)                              not null,
    currencyId  int                                   not null,
    amount      double    default 0                   not null,
    activeUntil timestamp default current_timestamp() not null
);
create table economy_users_booster_history
(
    id          int auto_increment
        primary key,
    playerId    char(36)  not null,
    currencyId  int       not null,
    amount      double    not null,
    activeUntil timestamp not null
);
create table economy_currencies
(
    id             int auto_increment primary key,
    context        int    default 0   not null,
    name           varchar(36)        not null,
    defaultBalance double default 0.0 not null,
    unique (context, name)
);
create table economy_currencies_booster
(
    id          int auto_increment                              primary key,
    currencyId  int                                             not null,
    amount      double                                          not null,
    startAt     timestamp default current_timestamp()           not null,
    activeUntil timestamp default adddate(current_timestamp, 5) not null
);
create table economy_currencies_booster_history
(
    id          int auto_increment
        primary key,
    currencyId  int       not null,
    amount      double    not null,
    startedAt   timestamp not null,
    activeUntil timestamp not null
);
```