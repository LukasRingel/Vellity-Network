package net.vellity.minecraft.common.spigot.friends.commands

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.friendsClient
import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.chat.broadcast.BroadcastService
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import net.vellity.minecraft.common.validator.Validators
import net.vellity.service.friends.Setting
import org.bukkit.entity.Player
import java.util.*

class MsgCommand : PlayerCommand("msg", "Send a private message to a player") {
  override fun executeCommand(player: Player, args: Array<out String>) {
    if (args.isEmpty() || args.size == 1) {
      player.sendTranslatedMessage("commons-friend-msg-command-syntax")
      return
    }

    val playerName = args[0]

    if (player.name.equals(playerName, true)) {
      player.sendTranslatedMessage("commons-friends-validator-name-self")
      return
    }

    if (!Validators.isValidMinecraftName(playerName)) {
      player.sendTranslatedMessage(
        "commons-friends-validator-name-minecraft",
        "name", playerName
      )
      return
    }

    if (messageCorrupt(player, Arrays.copyOfRange(args, 1, args.size).joinToString(" "))) {
      player.sendTranslatedMessage("commons-friends-validator-message-corrupt")
      return
    }

    val uuidResponse = usercacheClient.names().getUuidByName(playerName).execute()
    if (!uuidResponse.isSuccessful || uuidResponse.body() == null) {
      player.sendTranslatedMessage(
        "commons-friends-validator-name-unknown",
        "name", playerName
      )
      return
    }

    val uuid = uuidResponse.body()!!
    if (!Validators.isOnline(uuid)) {
      player.sendTranslatedMessage(
        "commons-friends-validator-name-offline",
        "name", playerName
      )
      return
    }

    if (!canSendMessageTo(player, uuid)) {
      player.sendTranslatedMessage(
        "commons-friend-msg-command-receiver-not-accepting",
        name,
        ColoredPlayerName.from(playerName, uuid, player.uniqueId).getColoredName()
      )
      return
    }

    val coloredSenderName = ColoredPlayerName.from(player.name, player.uniqueId, uuid).getColoredName()
    val coloredReceiverName = ColoredPlayerName.from(playerName, uuid, player.uniqueId).getColoredName()
    val message = Arrays.copyOfRange(args, 1, args.size).joinToString(" ")

    player.sendTranslatedMessage(
      "commons-friend-msg-command-sender",
      "sender", coloredSenderName,
      "receiver", coloredReceiverName,
      "message", message
    )

    BroadcastService.sendToOnePlayer(
      uuid,
      "commons-friend-msg-command-receiver",
      mapOf(
        "sender" to coloredSenderName,
        "receiver" to coloredReceiverName,
        "message" to message
      )
    )

  }

  private fun messageCorrupt(player: Player, message: String): Boolean {
    // player can ignore this check
    if (player.hasAccess(NetworkPermissions.COMMAND_PRIVATE_MESSAGE_IGNORE_CORRUPT_MESSAGES)) {
      return false
    }

    // message is not corrupt if it contains no minimessage formatting
    if (!message.contains(">") && !message.contains("<")) {
      return false
    }

    return message.split(" ")
      .any { word -> word.contains("<") && word.contains(">") }
  }

  private fun canSendMessageTo(player: Player, friendUUID: UUID): Boolean {
    if (player.hasAccess(NetworkPermissions.COMMAND_PRIVATE_MESSAGE_IGNORE_RESTRICTIONS)) {
      return true
    }
    val response = friendsClient.settings()
      .checkSetting(context, friendUUID, Setting.PRIVATE_MESSAGES, player.uniqueId, false)
      .execute()
    return response.isSuccessful && response.body() != null && response.body()!!
  }
}