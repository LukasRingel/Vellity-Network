package net.vellity.service.worker.worker.access

import net.vellity.service.worker.api.scheduled.ScheduledWorker
import net.vellity.service.worker.history.EnableHistory
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.*
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

@Component
@EnableHistory("access_groups_expired")
class MoveExpiredGroupsToHistory(private val mySqlConnection: MySqlConnection) :
  ScheduledWorker("access-groups-expired", 30, TimeUnit.MINUTES) {
  override fun run() {
    mySqlConnection.executeQuery("select * from access_users_groups where expireAt < now()") { resultSet ->
      Entry(
        resultSet.getInt("id"),
        resultSet.getContext("context"),
        resultSet.getUuid("player"),
        resultSet.getInt("group"),
        resultSet.getInstant("expireAt")
      )
    }
      .forEach { entry ->
        mySqlConnection.executeUpdate(
          "insert into access_users_groups_history (context, player, `group`, expireAt) values (?,?,?,?)",
          replacements = {
            it.setContext(1, entry.context)
            it.setUuid(2, entry.player)
            it.setInt(3, entry.group)
            it.setInstant(4, entry.expireAt)
          }
        )
        mySqlConnection.executeUpdate("delete from access_users_groups where id = ?", replacements = {
          it.setInt(1, entry.id)
        })
      }
  }

  private data class Entry(val id: Int, val context: Context, val player: UUID, val group: Int, val expireAt: Instant)
}