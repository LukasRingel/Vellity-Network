package net.vellity.minecraft.common.spigot.commands

import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.highestGroup
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.utils.configuration.isLifetime
import org.bukkit.entity.Player

class GroupCommand : PlayerCommand(
  name = "group",
  description = "Shows your current group",
  aliases = mutableListOf("rank", "perms")
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    val highestGroup = player.highestGroup()

    if (highestGroup == null) {
      player.sendTranslatedMessage("commons-command-group-default")
      return
    }

    if (isLifetime(highestGroup.expireAt)) {
      player.sendTranslatedMessage("commons-command-group-lifetime", "group", highestGroup.group)
      return
    }

    player.sendTranslatedMessage(
      "commons-command-group-expire",
      "group",
      highestGroup.group,
      "expireAt",
      FormattedDateTime.auto(highestGroup.expireAt)
    )
  }
}