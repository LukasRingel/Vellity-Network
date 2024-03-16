package net.vellity.utils.mysql.migration

interface Migration {
  fun shouldRun(): Boolean

  fun queries(): List<String>
}