package net.vellity.service.worker.worker.economy

import net.vellity.service.worker.api.scheduled.ScheduledWorker
import net.vellity.service.worker.history.EnableHistory
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.getInstant
import net.vellity.utils.mysql.extensions.getUuid
import net.vellity.utils.mysql.extensions.setInstant
import net.vellity.utils.mysql.extensions.setUuid
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.UUID
import java.util.concurrent.TimeUnit

@Component
@EnableHistory("economy_booster_expired")
class MoveExpiredBoosterToHistory(private val mySqlConnection: MySqlConnection) :
  ScheduledWorker("economy-booster-expired", 30, TimeUnit.MINUTES) {
  override fun run() {
    migrateUserBooster()
    migrateCurrencyBooster()
  }

  private fun migrateUserBooster() {
    mySqlConnection.executeQuery("select * from economy_users_booster where activeUntil < now()") { resultSet ->
      UserBoosterEntry(
        resultSet.getInt("id"),
        resultSet.getInt("currencyId"),
        resultSet.getUuid("playerId"),
        resultSet.getDouble("amount"),
        resultSet.getInstant("activeUntil")
      )
    }.forEach { entry ->
      mySqlConnection.executeUpdate(
        "insert into economy_users_booster_history (playerId, currencyId, amount, activeUntil) values (?,?,?,?)",
        replacements = {
          it.setUuid(1, entry.player)
          it.setInt(2, entry.currency)
          it.setDouble(3, entry.amount)
          it.setInstant(4, entry.activeUntil)
        }
      )
      mySqlConnection.executeUpdate("delete from economy_users_booster where id = ?", replacements = {
        it.setInt(1, entry.id)
      })
    }
  }

  private fun migrateCurrencyBooster() {
    mySqlConnection.executeQuery("select * from economy_currencies_booster where activeUntil < now()") { resultSet ->
      CurrencyBoosterEntry(
        resultSet.getInt("id"),
        resultSet.getInt("currencyId"),
        resultSet.getDouble("amount"),
        resultSet.getInstant("startAt"),
        resultSet.getInstant("activeUntil")
      )
    }.forEach { entry ->
      mySqlConnection.executeUpdate(
        "insert into economy_currencies_booster_history (currencyId, amount, startAt, activeUntil) values (?,?,?,?)",
        replacements = {
          it.setInt(1, entry.currency)
          it.setDouble(2, entry.amount)
          it.setInstant(3, entry.startAt)
          it.setInstant(4, entry.activeUntil)
        }
      )
      mySqlConnection.executeUpdate("delete from economy_currencies_booster where id = ?", replacements = {
        it.setInt(1, entry.id)
      })
    }
  }

  private data class UserBoosterEntry(
    val id: Int,
    val currency: Int,
    val player: UUID,
    val amount: Double,
    val activeUntil: Instant
  )

  private data class CurrencyBoosterEntry(
    val id: Int,
    val currency: Int,
    val amount: Double,
    val startAt: Instant,
    val activeUntil: Instant
  )
}