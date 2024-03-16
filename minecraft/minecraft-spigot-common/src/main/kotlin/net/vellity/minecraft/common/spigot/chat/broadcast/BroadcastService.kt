package net.vellity.minecraft.common.spigot.chat.broadcast

import net.vellity.minecraft.common.communication.Channels
import net.vellity.minecraft.common.redisClient
import net.vellity.minecraft.common.spigot.chat.broadcast.restriction.VisibilityRestriction
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.utils.configuration.toJson
import net.vellity.utils.redis.SimpleReceiver
import org.bukkit.Bukkit
import java.util.*

object BroadcastService {
  private const val TO_PLAYER_PREFIX = "TO-PLAYER"

  private val restrictions = mutableMapOf<String, VisibilityRestriction>()

  fun send(identifier: String, broadcastContent: BroadcastContent) {
    redisClient.asyncCommands()
      .publish(Channels.MESSAGE_BROADCAST, toJson(RemoteBroadcast.create(identifier, broadcastContent)))
      .whenComplete { _, _ -> }
  }

  fun sendToOnePlayer(player: UUID, message: String, map: Map<String, Any>) =
    sendToOnePlayer(player, BroadcastContent(message, map))

  fun sendToOnePlayer(player: UUID, broadcastContent: BroadcastContent) {
    if (Bukkit.getPlayer(player) != null) {
      Bukkit.getPlayer(player)!!.sendTranslatedMessage(
        broadcastContent.message,
        broadcastContent.replacements
      )
      return
    }
    send("$TO_PLAYER_PREFIX:$player", broadcastContent)
  }

  fun startListening() {
    redisClient.pubSubAsyncCommands().subscribe(Channels.MESSAGE_BROADCAST).get()
    redisClient.addListener(SimpleReceiver(Channels.MESSAGE_BROADCAST) { json ->
      val broadcast = RemoteBroadcast.createFromJson(json)

      if (broadcast.identifier.startsWith(TO_PLAYER_PREFIX)) {
        Bukkit.getPlayer(UUID.fromString(broadcast.identifier.substringAfter(":")))?.sendTranslatedMessage(
          broadcast.broadcastContent.message,
          broadcast.broadcastContent.replacements
        )
        return@SimpleReceiver
      }

      broadcastToWhoCanSeeLocally(broadcast)
    })
  }

  private fun broadcastToWhoCanSeeLocally(remoteBroadcast: RemoteBroadcast) {
    val restriction = restrictions[remoteBroadcast.identifier]
    val content = remoteBroadcast.broadcastContent

    if (restriction == null) {
      for (player in Bukkit.getOnlinePlayers()) {
        player.sendTranslatedMessage(
          content.message,
          content.replacements
        )
      }
      return
    }

    for (player in Bukkit.getOnlinePlayers()) {
      if (!restriction.canSee(player, remoteBroadcast)) {
        continue
      }
      player.sendTranslatedMessage(
        content.message,
        content.replacements
      )
    }
  }
}