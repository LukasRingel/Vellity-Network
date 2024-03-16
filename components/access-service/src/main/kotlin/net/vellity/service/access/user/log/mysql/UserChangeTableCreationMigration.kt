package net.vellity.service.access.user.log.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class UserChangeTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists access_audit_users_groups " +
      "( " +
      "    id        int auto_increment primary key, " +
      "    player    char(36)                              not null, " +
      "    groupId   int                                   not null, " +
      "    expireAt  timestamp                             not null, " +
      "    oldValue  bit                                   not null, " +
      "    newValue  bit                                   not null, " +
      "    createdAt timestamp default current_timestamp() not null " +
      ");",
    "create table if not exists access_audit_users_permissions " +
      "( " +
      "    id         int auto_increment primary key, " +
      "    player     char(36)                              not null, " +
      "    permission varchar(100)                          not null, " +
      "    context    int                                   not null, " +
      "    expireAt   timestamp                             not null, " +
      "    oldValue   bit                                   not null, " +
      "    newValue   bit                                   not null, " +
      "    createdAt  timestamp default current_timestamp() null " +
      "); "
  )
}