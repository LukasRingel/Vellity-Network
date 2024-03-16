package net.vellity.minecraft.common.spigot.language

import net.vellity.minecraft.common.babbelClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.language
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.updateLocalLanguageCache
import net.vellity.minecraft.common.spigot.extensions.sync
import net.vellity.minecraft.common.spigot.gui.GuiItem
import net.vellity.minecraft.common.spigot.item.ItemFactory
import net.vellity.service.babbel.LocaleConverter
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.function.Consumer

class LanguageItem(languageOption: LanguageOption, player: Player) : GuiItem(
  ItemFactory.playerHeadWithTexture(languageOption.texture)
    .name("commons-inventory-language-item")
    .lore("commons-inventory-language-lore")
    .replacements("language", languageOption.locale)
    .build(player),
  Consumer {
    val languageBefore = player.language()
    val localeResponse = babbelClient.users().updateLanguage(
      context,
      player.uniqueId,
      LocaleConverter.localeToString(languageOption.locale)
    ).execute()

    if (!localeResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-inventory-language-error")
      return@Consumer
    }

    player.updateLocalLanguageCache(languageOption.locale)
    player.sendTranslatedMessage("commons-inventory-language-success")
    Bukkit.getPluginManager().callEvent(
      LanguageChangeEvent(
        player,
        languageBefore,
        languageOption.locale
      )
    )
    sync { player.closeInventory() }
  }
)