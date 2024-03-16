package net.vellity.service.usercache.messages.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class MessagesTablesCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists usercache_users_messages " +
      "( " +
      "    id        int auto_increment " +
      "        primary key, " +
      "    player    char(36)                              not null, " +
      "    context   int                                   not null, " +
      "    message   text                                  not null, " +
      "    status    int                                   not null, " +
      "    createdAt timestamp default current_timestamp() not null " +
      ");",
    "create index if not exists usercache_users_messages_player_index " +
      "    on usercache_users_messages (player);"
  )
}