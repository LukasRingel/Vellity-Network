package net.vellity.utils.mysql.supplied

import java.sql.Connection

interface ConnectionSupplier {

  fun get(): Connection

  fun finishConnection(connection: Connection)

  fun close()

  fun connect()

}