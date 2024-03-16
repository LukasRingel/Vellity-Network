package net.vellity.service.access.user.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class AccessUserTableCreationMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries() = listOf(
    "create table if not exists access_users_groups( " +
      "id    int auto_increment primary key, " +
      "player   char(36)   not null, " +
      "context  int default 0 not null, " +
      "`group`  int     not null, " +
      "expireAt timestamp    not null, " +
      "constraint player unique (player, context, `group`));",
    "create table if not exists access_users_permissions( " +
      "id   int auto_increment primary key, " +
      "player  char(36)   not null, " +
      "context int default 0 not null, " +
      "permission varchar(48)   not null, " +
      "expireAt   timestamp    not null, " +
      "constraint player unique (player, permission, context));",
    "create table if not exists access_users_groups_history " +
      "( " +
      "  id       int auto_increment primary key, " +
      "  context  int       not null, " +
      "  player   char(36)  not null, " +
      "  `group`  int       not null, " +
      "  expireAt timestamp not null " +
      ");",
    "create table if not exists access_users_permissions_history " +
      "( " +
      "  id         int auto_increment primary key, " +
      "  context    int         not null, " +
      "  player     char(36)    not null, " +
      "  permission varchar(48) not null, " +
      "  expireAt   timestamp   not null " +
      ");"
  )
}