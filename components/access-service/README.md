# Service Explorer

## Description

This microservice is responsible for access restrictions (e.g. authentication, authorization, etc.) and for player
permissions on our networks.

## Decisions

- 26.07.2023: We open the time module via '--add-opens java.base/java.time=ALL-UNNAMED' to fix a bug in the mysql driver. This is a workaround
  until the bug is fixed in the driver.
- 27.06.2023: We switch to spring, since our own httpserver is trash.

## Dependencies

- utils-configuration ([README.md](../../utilities/utils-configuration/README.md))
- utils-redis ([README.md](../../utilities/utils-redis/README.md))
- utils-httpserver ([README.md](../../utilities/utils-httpserver/README.md))
- utils-mysql ([README.md](../../utilities/utils-mysql/README.md))
- access-common ([README.md](../../components/access-common/README.md))

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
create table access_groups
(
  id          int auto_increment primary key,
  context     int         not null,
  name        varchar(24) not null,
  activeUntil timestamp   not null
);
create table access_groups_meta_data
(
    id        int auto_increment primary key,
    groupId   int         not null,
    metaKey   varchar(24) not null,
    metaValue varchar(100) not null,
    constraint access_groups_meta_data_access_groups_id_fk
        foreign key (groupId) references access_groups (id)
);
create table access_groups_permissions
(
    id      int auto_increment primary key,
    groupId int         not null,
    name    varchar(48) not null,
    activeUntil timestamp   not null
);
create table access_groups_permissions_history
(
  id          int auto_increment primary key,
  groupId     int         not null,
  permission  varchar(48) not null,
  activeUntil timestamp   not null
);
create table access_users_groups
(
    id       int auto_increment primary key,
    player   char(36)      not null,
    context  int default 0 not null,
    `group`  int           not null,
    expireAt timestamp     not null,
    constraint player
        unique (player, context, `group`)
);
create table access_users_groups_history
(
  id       int auto_increment primary key,
  context  int       not null,
  player   char(36)  not null,
  `group`  int       not null,
  expireAt timestamp not null
);
create table access_users_permissions
(
    id         int auto_increment primary key,
    player     char(36)      not null,
    context    int default 0 not null,
    permission varchar(48)   not null,
    expireAt   timestamp     not null,
    constraint player
        unique (player, permission, context)
);
create table access_users_permissions_history
(
  id         int auto_increment primary key,
  context    int         not null,
  player     char(36)    not null,
  permission varchar(48) not null,
  expireAt   timestamp   not null
);
```

### default values

```sql
insert into access_groups (context, name, activeUntil) values (1, 'administrator', '2030-01-01 00:00:00');
insert into access_groups (context, name, activeUntil) values (1, 'developer', '2030-01-01 00:00:00');
insert into access_groups (context, name, activeUntil) values (1, 'player', '2030-01-01 00:00:00');
insert into access_groups_meta_data (groupId, metaKey, metaValue) values (1, 'PREFIX_TAB_LIST', 'Admin');
insert into access_groups_meta_data (groupId, metaKey, metaValue) values (2, 'PREFIX_TAB_LIST', 'Dev');
insert into access_groups_meta_data (groupId, metaKey, metaValue) values (3, 'PREFIX_TAB_LIST', '');
insert into access_groups_permissions (groupId, name, activeUntil) values (1, '*', '2030-01-01 00:00:00');
insert into access_groups_permissions (groupId, name, activeUntil) values (2, 'vellity.dev', '2030-01-01 00:00:00');
insert into access_groups_permissions (groupId, name, activeUntil) values (3, 'vellity.join', '2030-01-01 00:00:00');
insert into access_users_groups (player, context, `group`, expireAt) values ('8bc61e5e-4791-4518-88ef-16448910296f', 1, 1, '2030-01-01 00:00:00');
insert into access_users_groups (player, context, `group`, expireAt) values ('2fbe3e3e-50c3-482c-872b-28386fd91704', 1, 2, '2030-01-01 00:00:00');
insert into access_users_groups (player, context, `group`, expireAt) values ('069a79f4-44e9-4726-a5be-fca90e38aaf5', 1, 3, '2030-01-01 00:00:00');
```