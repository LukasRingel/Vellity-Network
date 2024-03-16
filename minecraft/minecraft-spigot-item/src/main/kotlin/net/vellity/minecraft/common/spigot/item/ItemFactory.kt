package net.vellity.minecraft.common.spigot.item

import org.bukkit.Material
import java.util.UUID

object ItemFactory {
  fun item(material: Material): ItemBlueprint {
    return ItemBlueprint(material)
  }

  fun playerHead(): ItemBlueprint {
    return ItemBlueprint(Material.PLAYER_HEAD)
  }

  fun playerHeadOf(playerUUID: UUID): ItemBlueprint {
    return ItemBlueprint(Material.PLAYER_HEAD).textureFromUserCache(playerUUID)
  }

  fun playerHeadWithTexture(texture: String): ItemBlueprint {
    return ItemBlueprint(Material.PLAYER_HEAD).texture(texture)
  }

  fun playerHeadFromMineSkin(mineSkinId: String): ItemBlueprint {
    return ItemBlueprint(Material.PLAYER_HEAD).textureFromMineSkinUrl(mineSkinId)
  }
}