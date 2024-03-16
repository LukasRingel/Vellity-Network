package net.vellity.minecraft.common.translations.transformer.player.color

import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.access.MetaData
import java.util.*

class PrefixedPlayerName(private val name: String, private val uuid: UUID, private val viewer: UUID = uuid) {
  fun getPrefixedName(): String {
    val response = accessClient.users().get(uuid, context).execute()
    if (!response.isSuccessful) {
      return getDefaultColoring()
    }

    val user = response.body()!!
    user.groups.find { it.group.hasMetaData(MetaData.PREFIX_CHAT.name) }?.let {
      return getColoring(it.group.getMetaDataValue(MetaData.PREFIX_CHAT.name)!!)
    }

    return getDefaultColoring()
  }

  private fun getColoring(color: String): String {
    return "$color$name"
  }

  private fun getDefaultColoring(): String {
    return getColoring("<${TranslationRepository.getColorString("default-player-color")}>")
  }

  companion object {
    fun from(name: String, uuid: UUID, viewer: UUID = uuid): PrefixedPlayerName {
      return PrefixedPlayerName(name, uuid, viewer)
    }

    fun from(uuid: UUID): PrefixedPlayerName {
      usercacheClient.names().getNameByUuid(uuid).execute().body()?.let {
        return PrefixedPlayerName(it.name, uuid)
      }
      return PrefixedPlayerName("???", uuid)
    }
  }
}