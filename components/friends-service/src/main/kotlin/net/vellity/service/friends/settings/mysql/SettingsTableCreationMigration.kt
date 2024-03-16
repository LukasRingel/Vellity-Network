package net.vellity.service.friends.settings.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class SettingsTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists friends_users_settings " +
      "( " +
      "    id      int auto_increment primary key, " +
      "    context int                not null, " +
      "    player  char(36)           not null, " +
      "    setting int                not null, " +
      "    status  int                not null, " +
      "    unique (context, player, setting) " +
      ");"
  )
}