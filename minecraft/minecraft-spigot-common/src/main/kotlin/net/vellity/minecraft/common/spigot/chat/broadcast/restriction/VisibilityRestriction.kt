package net.vellity.minecraft.common.spigot.chat.broadcast.restriction

import net.vellity.minecraft.common.spigot.chat.broadcast.RemoteBroadcast
import org.bukkit.entity.Player

interface VisibilityRestriction {
  fun canSee(player: Player, remoteBroadcast: RemoteBroadcast): Boolean
}