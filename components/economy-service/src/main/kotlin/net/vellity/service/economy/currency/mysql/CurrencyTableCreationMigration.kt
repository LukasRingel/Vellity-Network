package net.vellity.service.economy.currency.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class CurrencyTableCreationMigration : OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries() = listOf(
    "create table if not exists economy_currencies " +
      "( " +
      "    id             int auto_increment primary key, " +
      "    context        int    default 0   not null, " +
      "    name           varchar(36)        not null, " +
      "    defaultBalance double default 0.0 not null, " +
      "    unique (context, name) " +
      ");",
    "create table if not exists economy_currencies_booster " +
      "( " +
      "    id          int auto_increment                              primary key, " +
      "    currencyId  int                                             not null, " +
      "    amount      double                                          not null, " +
      "    startAt     timestamp default current_timestamp           not null, " +
      "    activeUntil timestamp default current_timestamp not null " +
      ");",
    "create table if not exists economy_currencies_booster_history " +
      "( " +
      "    id          int auto_increment primary key, " +
      "    currencyId  int       not null, " +
      "    amount      double    not null, " +
      "    startedAt   timestamp not null, " +
      "    activeUntil timestamp not null " +
      ");"
  )
}