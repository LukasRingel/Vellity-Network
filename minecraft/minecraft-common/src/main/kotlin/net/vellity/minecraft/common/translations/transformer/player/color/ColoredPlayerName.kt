package net.vellity.minecraft.common.translations.transformer.player.color

import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.access.MetaData
import net.vellity.service.usercache.combined.TextureAndName
import java.util.*

class ColoredPlayerName(private val name: String, private val uuid: UUID, private val viewer: UUID = uuid) {

  fun getColoredName(): String {
    val response = accessClient.users().get(uuid, context).execute()
    if (!response.isSuccessful) {
      return getDefaultColoring()
    }

    val user = response.body()!!
    user.groups.find { it.group.hasMetaData(MetaData.COLOR_CHAT.name) }?.let {
      return getColoring(it.group.getMetaDataValue(MetaData.COLOR_CHAT.name)!!)
    }

    return getDefaultColoring()
  }

  private fun getColoring(color: String): String {
    return "<$color>$name</$color>"
  }

  private fun getDefaultColoring(): String {
    return getColoring(TranslationRepository.getColorString("default-player-color"))
  }

  companion object {
    fun from(name: String, uuid: UUID, viewer: UUID = uuid): ColoredPlayerName {
      return ColoredPlayerName(name, uuid, viewer)
    }

    fun from(uuid: UUID): ColoredPlayerName {
      usercacheClient.names().getNameByUuid(uuid).execute().body()?.let {
        return ColoredPlayerName(it.name, uuid)
      }
      return ColoredPlayerName("???", uuid)
    }

    fun from(textureAndName: TextureAndName, viewer: UUID = textureAndName.uuid): ColoredPlayerName {
      return ColoredPlayerName(textureAndName.name, textureAndName.uuid, viewer)
    }
  }
}