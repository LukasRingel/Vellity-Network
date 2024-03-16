package net.vellity.service.usercache.names.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class NamesTablesCreateMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists usercache_users_names " +
      "( " +
      "    id   int auto_increment primary key, " +
      "    uuid char(36)    not null    unique, " +
      "    name varchar(16) not null " +
      ");",
    "create table if not exists usercache_users_names_history " +
      "( " +
      "    id              int auto_increment " +
      "        primary key, " +
      "    player          char(36)                            not null, " +
      "    name            varchar(16)                         not null, " +
      "    updatedAt       timestamp default current_timestamp not null " +
      ");"
  )
}