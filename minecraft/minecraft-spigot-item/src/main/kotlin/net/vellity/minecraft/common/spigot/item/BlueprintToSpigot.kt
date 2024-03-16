package net.vellity.minecraft.common.spigot.item

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.kyori.adventure.text.format.TextDecoration
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.spigot.item.naming.NamingStrategy
import net.vellity.utils.configuration.Environment
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

class BlueprintToSpigot {
  companion object {
    var localeForPlayer: (Player) -> Locale = { player -> player.locale() }

    fun forEverybody(blueprint: ItemBlueprint): ItemStack {
      val item = ItemStack(blueprint.type, blueprint.amount)
      val itemMeta = item.itemMeta
      itemMeta.displayName(MiniMessageFormat.format(blueprint.name))
      itemMeta.lore(blueprint.lore.map { MiniMessageFormat.format(it) })
      applyItemSettings(blueprint, item, itemMeta)
      return item
    }

    fun forPlayer(blueprint: ItemBlueprint, player: Player): ItemStack {
      val item = ItemStack(blueprint.type, blueprint.amount)
      val itemMeta = item.itemMeta
      applyNaming(blueprint.namingStrategy, itemMeta, blueprint, player)
      applyItemSettings(blueprint, item, itemMeta)
      return item
    }

    private fun applyItemSettings(blueprint: ItemBlueprint, stack: ItemStack, meta: ItemMeta) {
      applySkullTexture(stack, meta, blueprint)

      if (blueprint.leatherColor != null) {
        (meta as LeatherArmorMeta).setColor(blueprint.leatherColor)
      }

      stack.itemMeta = meta
      stack.addItemFlags(*blueprint.flags.toTypedArray())
    }

    private fun applyNaming(
      namingStrategy: NamingStrategy,
      itemMeta: ItemMeta,
      blueprint: ItemBlueprint,
      player: Player
    ) {
      if (namingStrategy == NamingStrategy.PLAIN) {
        itemMeta.displayName(MiniMessageFormat.format(blueprint.name))
        itemMeta.lore(blueprint.lore.map { MiniMessageFormat.format(it) })
        return
      }

      itemMeta.displayName(
        MiniMessageFormat.format(
          TranslationRepository.get(
            blueprint.name,
            localeForPlayer(player),
            blueprint.replacements
          )
        ).decoration(TextDecoration.ITALIC, false)
      )

      if (blueprint.lore.isEmpty()) {
        return
      }

      val loreComponents = blueprint.lore.map {
        MiniMessageFormat.formatMultiLine(
          TranslationRepository.getMaybeMultiLine(
            it,
            localeForPlayer(player),
            blueprint.replacements
          )
        ) { component -> component.decoration(TextDecoration.ITALIC, false) }
      }.flatten()
      itemMeta.lore(loreComponents)
    }

    private fun applySkullTexture(itemStack: ItemStack, itemMeta: ItemMeta, blueprint: ItemBlueprint) {
      if (itemStack.type != Material.PLAYER_HEAD) {
        return
      }
      val profileField = skullMetaClass.getDeclaredField("profile")
      profileField.trySetAccessible()
      profileField[itemMeta] = buildGameProfile(blueprint.textureStrategy.textureValue())
    }

    private fun buildGameProfile(textureId: String): GameProfile {
      val profile = GameProfile(UUID.randomUUID(), "")
      val properties = profile.properties
      val property = Property("textures", textureId)
      properties.put("textures", property)
      return profile
    }


    private val skullMetaClass = Class.forName(
      "org.bukkit.craftbukkit." +
        Environment.getOrDefault("BUKKIT_VERSION_NAME", "v1_20_R1") +
        ".inventory.CraftMetaSkull"
    )
  }
}