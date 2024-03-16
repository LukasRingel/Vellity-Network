package net.vellity.service.usercache.login.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class LoginTablesCreateMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists usercache_users_logins " +
      "( " +
      "    id      int auto_increment primary key, " +
      "    context int                                 not null, " +
      "    uuid    char(36)                            not null, " +
      "    address text                                not null, " +
      "    date    timestamp default current_timestamp not null " +
      ");",
    "create table if not exists usercache_users_logouts " +
      "( " +
      "    id      int auto_increment " +
      "        primary key, " +
      "    context int       not null, " +
      "    uuid    char(36)  not null, " +
      "    login   int       not null, " +
      "    date    timestamp default current_timestamp() not null " +
      ");"
  )
}