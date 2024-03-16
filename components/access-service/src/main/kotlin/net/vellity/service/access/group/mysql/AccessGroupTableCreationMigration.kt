package net.vellity.service.access.group.mysql

import net.vellity.utils.mysql.migration.OnlyOnDevelopmentEnvironmentMigration

class AccessGroupTableCreationMigration: OnlyOnDevelopmentEnvironmentMigration() {
  override fun queries(): List<String> = listOf(
    "create table if not exists access_groups  " +
      "(  " +
      "    id      int auto_increment primary key,  " +
      "    context int         not null,  " +
      "    name    varchar(24) not null," +
      "    activeUntil timestamp   not null  " +
      ");",
    "create table if not exists access_groups_meta_data  " +
      "(  " +
      "    id        int auto_increment primary key,  " +
      "    groupId   int         not null,  " +
      "    metaKey   varchar(24) not null,  " +
      "    metaValue varchar(48) not null,  " +
      "    constraint access_groups_meta_data_access_groups_id_fk  " +
      "        foreign key (groupId) references access_groups (id)  " +
      ");",
    "create table if not exists access_groups_permissions  " +
      "(  " +
      "    id      int auto_increment primary key,  " +
      "    groupId int         not null,  " +
      "    name    varchar(48) not null," +
      "    activeUntil timestamp   not null  " +
      ");",
    "create table if not exists access_groups_permissions_history " +
      "( " +
      "  id          int auto_increment primary key, " +
      "  groupId     int         not null, " +
      "  permission  varchar(48) not null, " +
      "  activeUntil timestamp   not null " +
      ");"
  )
}