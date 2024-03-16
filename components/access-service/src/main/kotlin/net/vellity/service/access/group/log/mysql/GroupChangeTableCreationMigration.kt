package net.vellity.service.access.group.log.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class GroupChangeTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists access_audit_groups_meta_data (" +
      "    id        int auto_increment                 primary key," +
      "    groupId   int                                   not null," +
      "    metadata  varchar(50)                           not null," +
      "    oldValue  text                                  not null," +
      "    newValue  text                                  not null," +
      "    createdAt timestamp default current_timestamp() not null" +
      ");",
    "create table if not exists access_audit_groups_permissions (" +
      "    id         int auto_increment                 primary key," +
      "    groupId    int                                   not null," +
      "    permission varchar(100)                          not null," +
      "    oldValue   bit                                   not null," +
      "    newValue   bit                                   not null," +
      "    createdAt  timestamp default current_timestamp() not null" +
      ");"
  )
}