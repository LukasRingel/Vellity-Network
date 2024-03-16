package net.vellity.minecraft.common.spigot.chat.format

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import java.util.UUID

interface MessageFormat {
  fun format(senderUuid: UUID, senderName: String, message: String, viewer: Player): Component
}