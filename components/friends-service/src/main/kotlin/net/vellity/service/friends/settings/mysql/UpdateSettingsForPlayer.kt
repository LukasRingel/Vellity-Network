package net.vellity.service.friends.settings.mysql

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.extensions.setContext
import net.vellity.utils.mysql.extensions.setEnum
import net.vellity.utils.mysql.extensions.setUuid
import net.vellity.utils.mysql.query.UpdateQuery
import java.sql.PreparedStatement
import java.util.*

class UpdateSettingsForPlayer(
  private val context: Context,
  private val player: UUID,
  private val setting: Setting,
  private val value: SettingState
) : UpdateQuery {
  override fun query(): String {
    return "insert into friends_users_settings (context, player, setting, status) values (?, ?, ?, ?) on duplicate key update status = ?;"
  }

  override fun replace(preparedStatement: PreparedStatement) {
    preparedStatement.setContext(1, context)
    preparedStatement.setUuid(2, player)
    preparedStatement.setEnum(3, setting)
    preparedStatement.setEnum(4, value)
    preparedStatement.setEnum(5, value)
  }
}