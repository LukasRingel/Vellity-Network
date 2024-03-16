package net.vellity.service.friends.friendship.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class FriendshipsTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists friends_users_friends " +
      "( " +
      "    id          int auto_increment                  primary key, " +
      "    context     int                                 not null, " +
      "    player      char(36)                            not null, " +
      "    friend      char(36)                            not null, " +
      "    state       int                                 not null, " +
      "    activeUntil timestamp                           not null, " +
      "    changedAt   timestamp default current_timestamp not null, " +
      "    createdAt   timestamp default current_timestamp not null " +
      ");",
    "create table if not exists friends_users_requests " +
      "( " +
      "    id          int auto_increment                  primary key, " +
      "    context     int                                 not null, " +
      "    player      char(36)                            not null, " +
      "    target      char(36)                            not null, " +
      "    activeUntil timestamp                           not null, " +
      "    createdAt   timestamp default current_timestamp not null " +
      ");",
    "create table if not exists friends_users_blocked " +
      "( " +
      "    id          int auto_increment                  primary key, " +
      "    context     int                                 not null, " +
      "    player      char(36)                            not null, " +
      "    target      char(36)                            not null, " +
      "    activeUntil timestamp                           not null, " +
      "    createdAt   timestamp default current_timestamp not null " +
      ");"
  )
}