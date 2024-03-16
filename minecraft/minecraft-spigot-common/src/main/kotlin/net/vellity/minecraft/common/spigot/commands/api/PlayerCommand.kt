package net.vellity.minecraft.common.spigot.commands.api

import net.vellity.minecraft.common.spigot.VellityNetworkSpigotPlugin
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.Executors

abstract class PlayerCommand(
  name: String,
  description: String,
  private val permission: String = "",
  aliases: MutableList<String> = mutableListOf()
) : Command(name, description, "/$name", aliases) {

  override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>?): Boolean {
    if (sender !is Player) {
      return true
    }

    if (permission.isEmpty()) {
      executors.submit {
        executeCommand(sender, args ?: emptyArray())
      }
      return true
    }

    executors.submit {
      if (!sender.hasAccess(permission)) {
        sendAccessErrorMessage(sender)
        return@submit
      }

      executeCommand(sender, args ?: emptyArray())
    }

    return false
  }

  abstract fun executeCommand(player: Player, args: Array<out String>)

  fun sendAccessErrorMessage(player: Player) {
    player.sendTranslatedMessage("commons-command-error-access")
  }

  companion object {
    private val executors = Executors.newCachedThreadPool()
  }
}