package net.vellity.service.babbel.user.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class UserTableCreationMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries() = listOf(
    "create table if not exists babbel_users_locales ( " +
      "    id        int auto_increment primary key, " +
      "    player_id char(36)                    not null, " +
      "    context   int                         not null, " +
      "    locale    varchar(10) default 'en_US' not null, " +
      "    constraint player_id unique (player_id, context) " +
      ");"
  )
}