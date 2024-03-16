package net.vellity.utils.mysql.supplied

import java.io.File
import java.sql.Connection
import java.sql.DriverManager

class SqlLiteConnectionSupplier : ConnectionSupplier {

  private lateinit var connection: Connection

  override fun get(): Connection {
    return connection
  }

  override fun finishConnection(connection: Connection) {
    // do nothing
  }

  override fun close() {
    connection.close()
  }

  override fun connect() {
    val dbFile = System.getenv("APPDATA") + File.separator + "vellity" + File.separator + "network.db"
    with(File(dbFile)) {
      if (!parentFile.exists()) {
        parentFile.mkdirs()
      }
    }
    connection = DriverManager.getConnection("jdbc:sqlite:$dbFile")
  }
}