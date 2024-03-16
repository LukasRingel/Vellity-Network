package net.vellity.service.friends.settings.mysql

import net.vellity.service.friends.Setting
import net.vellity.service.friends.SettingState
import net.vellity.service.friends.settings.FriendSettingsRepository
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import org.springframework.stereotype.Component
import java.util.*

@Component
class MySqlSettingsRepository(private val mySqlConnection: MySqlConnection) : FriendSettingsRepository {
  override fun getSettings(context: Context, player: UUID): List<Pair<Int, Int>> {
    return mySqlConnection.executeQuery(SelectSettingsForPlayer(context, player))
  }

  override fun updateSetting(context: Context, player: UUID, setting: Setting, value: SettingState) {
    mySqlConnection.executeUpdate(UpdateSettingsForPlayer(context, player, setting, value))
  }
}