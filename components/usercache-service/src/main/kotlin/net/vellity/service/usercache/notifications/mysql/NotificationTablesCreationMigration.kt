package net.vellity.service.usercache.notifications.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class NotificationTablesCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists usercache_users_notifications " +
      "( " +
      "    id           int auto_increment " +
      "        primary key, " +
      "    player       char(36)                              not null, " +
      "    context      int                                   not null, " +
      "    notification int                                   not null, " +
      "    status       bit                                   not null, " +
      "    activeUntil  timestamp                             not null, " +
      "    createdAt    timestamp default current_timestamp() not null, " +
      "    updatedAt    timestamp default current_timestamp() null on update current_timestamp(), " +
      "    constraint usercache_users_notifications_player_index " +
      "        unique (player, context, notification) " +
      ");"
  )
}