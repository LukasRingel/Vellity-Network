package net.vellity.service.economy.user.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class UserTableCreationMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries() = listOf(
    "create table if not exists economy_users_balance " +
      "( " +
      "    id       int auto_increment primary key, " +
      "    playerId char(36)           not null, " +
      "    balance  double default 0   not null, " +
      "    currency int                not null, " +
      "    unique (playerId, currency) " +
      ");",
    "create table if not exists economy_users_transactions " +
      "( " +
      "    id        int auto_increment                  primary key, " +
      "    playerId  char(36)                            not null, " +
      "    currency  int                                 not null, " +
      "    amount    double                              not null, " +
      "    action    int                                 not null, " +
      "    createdAt timestamp default current_timestamp not null " +
      ");",
    "create table if not exists economy_users_booster " +
      "( " +
      "    id          int auto_increment                 primary key," +
      "    playerId    char(36)                              not null," +
      "    currencyId  int                                   not null," +
      "    amount      double    default 0                   not null," +
      "    activeUntil timestamp default current_timestamp() not null " +
      ");",
    "create table if not exists economy_users_booster_history " +
      "( " +
      "    id          int auto_increment primary key," +
      "    playerId    char(36)  not null," +
      "    currencyId  int       not null," +
      "    amount      double    not null," +
      "    activeUntil timestamp not null " +
      "); "
  )
}