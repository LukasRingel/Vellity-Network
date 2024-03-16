package net.vellity.minecraft.common.spigot.item

import net.vellity.minecraft.common.spigot.item.naming.NamingStrategy
import net.vellity.minecraft.common.spigot.item.textures.MineSkinStrategy
import net.vellity.minecraft.common.spigot.item.textures.ProvidedStrategy
import net.vellity.minecraft.common.spigot.item.textures.TextureStrategy
import net.vellity.minecraft.common.spigot.item.textures.UserCacheStrategy
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemBlueprint(var type: Material = Material.BARRIER) {
  var name: String = ""
  var replacements: Array<Any> = arrayOf()
  var lore: List<String> = listOf()
  var amount: Int = 1
  var textureStrategy: TextureStrategy = ProvidedStrategy()
  var namingStrategy: NamingStrategy = NamingStrategy.TRANSLATED
  var flags: List<ItemFlag> = listOf()
  var leatherColor: Color? = null

  fun type(type: Material): ItemBlueprint {
    this.type = type
    return this
  }

  fun name(name: String, namingStrategy: NamingStrategy = NamingStrategy.TRANSLATED): ItemBlueprint {
    this.name = name
    this.namingStrategy = namingStrategy
    return this
  }

  fun amount(amount: Int): ItemBlueprint {
    this.amount = amount
    return this
  }

  fun replacements(vararg replacements: Any): ItemBlueprint {
    this.replacements = arrayOf(*replacements)
    return this
  }

  fun lore(lore: List<String>): ItemBlueprint {
    this.lore = lore
    return this
  }

  fun lore(vararg lore: String): ItemBlueprint {
    this.lore = listOf(*lore)
    return this
  }

  fun texture(textureValue: String): ItemBlueprint {
    this.textureStrategy = ProvidedStrategy(textureValue)
    return this
  }

  fun textureFromMineSkinUrl(url: String): ItemBlueprint {
    this.textureStrategy = MineSkinStrategy(url)
    return this
  }

  fun textureFromUserCache(uuid: UUID): ItemBlueprint {
    this.textureStrategy = UserCacheStrategy(uuid)
    return this
  }

  fun hideFlags(vararg flags: ItemFlag): ItemBlueprint {
    this.flags = listOf(*flags)
    return this
  }

  fun leatherColor(color: Color): ItemBlueprint {
    this.leatherColor = color
    return this
  }

  fun build(): ItemStack {
    return BlueprintToSpigot.forEverybody(this)
  }

  fun build(player: Player): ItemStack {
    return BlueprintToSpigot.forPlayer(this, player)
  }
}