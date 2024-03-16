package net.vellity.minecraft.common.spigot.commands

import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.translations.TranslationRepository
import org.bukkit.entity.Player

class UpdateLocalesCommand : PlayerCommand(
  "updatelocales",
  "Updates the locales",
  NetworkPermissions.COMMAND_UPDATE_LOCALES) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    TranslationRepository.updateRepository()
  }
}