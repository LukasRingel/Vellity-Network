package net.vellity.service.guardian.report.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class ReportTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists guardian_reports_reasons " +
      "( " +
      "    id          int auto_increment primary key, " +
      "    context     int                   not null, " +
      "    name        varchar(24)           not null, " +
      "    activeUntil timestamp             not null, " +
      "    constraint context " +
      "        unique (context, name) " +
      ");",
    "create table if not exists guardian_reports " +
      "( " +
      "    id          int auto_increment " +
      "        primary key, " +
      "    context     int                                   not null, " +
      "    target      char(36)                              not null, " +
      "    state       int                                   not null, " +
      "    reason      int                                   not null, " +
      "    creator     char(36)                              not null, " +
      "    activeUntil timestamp                             not null, " +
      "    createdAt   timestamp default current_timestamp() not null " +
      ");",
    "create table if not exists guardian_reports_actions " +
      "( " +
      "    id        int auto_increment " +
      "        primary key, " +
      "    reportId  int                                 not null, " +
      "    player    char(36)                            not null, " +
      "    action    int                                 not null, " +
      "    createdAt timestamp default current_timestamp null " +
      ");"
  )
}