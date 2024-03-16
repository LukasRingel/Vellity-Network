package net.vellity.service.usercache.textures.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class TexturesTablesCreationMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists usercache_users_textures " +
      "( " +
      "    id        int auto_increment primary key, " +
      "    uuid      char(36) not null unique, " +
      "    signature text     not null, " +
      "    value     text     not null " +
      ");",
    "create table if not exists usercache_users_textures_history " +
      "( " +
      "    id        int auto_increment " +
      "        primary key, " +
      "    player    char(36)                            not null, " +
      "    signature text                                not null, " +
      "    value     text                                not null, " +
      "    changedAt timestamp default current_timestamp not null " +
      ");"
  )
}