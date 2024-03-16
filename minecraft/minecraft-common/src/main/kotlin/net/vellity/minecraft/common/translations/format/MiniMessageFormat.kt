package net.vellity.minecraft.common.translations.format

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.vellity.service.babbel.ColorConfiguration

object MiniMessageFormat {
  private val miniMessage: MiniMessage = MiniMessage.builder().build()
  private var tagResolvers = mutableListOf<TagResolver>()

  fun format(message: String): Component {
    return miniMessage.deserialize(message, *tagResolvers.toTypedArray())
  }

  fun formatMultiLine(messages: Array<String>, formatting: (Component) -> Component = {it}): List<Component> {
    return messages.map { formatting(miniMessage.deserialize(it, *tagResolvers.toTypedArray())) }
  }

  fun componentToString(component: Component): String {
    return miniMessage.serialize(component)
  }

  fun addResolver(resolver: TagResolver) {
    tagResolvers.add(resolver)
  }

  fun addResolvers(colorConfiguration: ColorConfiguration) {
    colorConfiguration.colors.forEach { (key, value) ->
      val textColor = TextColor.fromCSSHexString(value)
      addResolver(TagResolver.resolver(key, Tag.styling { it.color(textColor) }))
      addResolver(TagResolver.resolver(formatColorKey(key), Tag.styling { it.color(textColor) }))
    }
  }

  fun clearResolvers() {
    tagResolvers.clear()
  }

  private fun formatColorKey(key: String): String {
    return key.replace("color-", "")
      .replace("-color", "")
  }
}