package net.vellity.minecraft.common.spigot.chat.broadcast.restriction

import net.vellity.minecraft.common.spigot.chat.broadcast.RemoteBroadcast
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import org.bukkit.entity.Player

class AccessRestriction(private val permission: String) : VisibilityRestriction {
  override fun canSee(player: Player, remoteBroadcast: RemoteBroadcast): Boolean {
    return player.hasAccess(permission)
  }
}