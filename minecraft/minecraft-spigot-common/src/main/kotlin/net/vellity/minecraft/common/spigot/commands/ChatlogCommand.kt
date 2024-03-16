package net.vellity.minecraft.common.spigot.commands

import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.guardianClient
import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.guardian.chatlog.Chatlog
import net.vellity.service.usercache.message.Message
import org.bukkit.entity.Player
import retrofit2.Response
import java.time.Clock
import java.time.temporal.ChronoUnit
import java.util.*

class ChatlogCommand : PlayerCommand("chatlog", "Log messages of a player to proof misbehaviour") {
  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.isEmpty()) {
      player.sendTranslatedMessage("commons-command-chatlog-help")
      return
    }

    val playerName = args[0]

    val uuidResponse = tryRequestUuidForName(playerName)
    if (!uuidResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-command-chatlog-error-player", "player", playerName)
      return
    }

    val playerUuid = uuidResponse.body()!!
    if (playerIsNotAllowedToChatlogTarget(player, playerUuid)) {
      player.sendTranslatedMessage(
        "commons-command-chatlog-error-ignore",
        "player",
        ColoredPlayerName.from(playerName, playerUuid)
      )
      return
    }

    val sentMessagesResponse = tryRequestMessagesOfLast15Minutes(player, playerName, playerUuid)

    if (sentMessagesResponse.isEmpty()) {
      player.sendTranslatedMessage(
        "commons-command-chatlog-error-messages",
        "player",
        ColoredPlayerName.from(playerName, playerUuid)
      )
      return
    }

    val chatlogResponse = tryCreateChatlog(player, playerUuid)
    if (!chatlogResponse.isSuccessful) {
      player.sendTranslatedMessage(
        "commons-command-chatlog-error-create",
        "player",
        ColoredPlayerName.from(playerName, playerUuid)
      )
      return
    }

    player.sendTranslatedMessage(
      "commons-command-chatlog-success",
      "player",
      ColoredPlayerName.from(playerName, playerUuid),
      "id",
      chatlogResponse.body()!!.id
    )
  }

  private fun tryRequestUuidForName(playerName: String): Response<UUID> =
    usercacheClient.names().getUuidByName(playerName).execute()

  private fun tryCreateChatlog(
    player: Player,
    playerUuid: UUID
  ): Response<Chatlog> =
    guardianClient.chatlogs().createChatLog(context, player.uniqueId, playerUuid).execute()

  private fun tryRequestMessagesOfLast15Minutes(player: Player, playerName: String, playerUuid: UUID): List<Message> {
    val listResponse = usercacheClient.messages().getMessagesBetween(
      context,
      playerUuid,
      Clock.systemUTC().instant().minus(15, ChronoUnit.MINUTES).toString(),
      Clock.systemUTC().instant().toString()
    ).execute()
    if (!listResponse.isSuccessful) {
      player.sendTranslatedMessage(
        "commons-command-chatlog-error-create",
        "player",
        ColoredPlayerName.from(playerName, playerUuid)
      )
      return emptyList()
    }
    return listResponse.body()!!
  }

  private fun playerIsNotAllowedToChatlogTarget(player: Player, target: UUID): Boolean {
    if (player.hasAccess(NetworkPermissions.COMMAND_CHATLOG_EVERYBODY)) {
      return false
    }

    val response = accessClient.users().hasPermission(
      target,
      context,
      NetworkPermissions.COMMAND_CHATLOG_IGNORE
    ).execute()

    if (!response.isSuccessful) {
      return false
    }

    return response.body()!!
  }
}