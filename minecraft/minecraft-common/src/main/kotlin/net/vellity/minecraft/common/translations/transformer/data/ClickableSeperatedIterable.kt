package net.vellity.minecraft.common.translations.transformer.data

import net.kyori.adventure.text.event.ClickEvent

class ClickableSeperatedIterable(
  val iterable: Iterable<Any>,
  val clickEvent: (Any) -> ClickEvent,
  val seperator: String = ", ",
  private val dataColor: (Any) -> String = { "info" }
) {
  fun decorate(any: Any, text: String): String {
    val color = dataColor(any)
    return "<$color>$text</$color>"
  }

  companion object {
    fun of(iterable: Iterable<Any>, clickEvent: (Any) -> ClickEvent): ClickableSeperatedIterable {
      return ClickableSeperatedIterable(iterable, clickEvent)
    }

    fun of(vararg iterable: Any, clickEvent: (Any) -> ClickEvent): ClickableSeperatedIterable {
      return ClickableSeperatedIterable(iterable.toList(), clickEvent)
    }

    fun of(
      iterable: Iterable<Any>,
      clickEvent: (Any) -> ClickEvent,
      color: (Any) -> String
    ): ClickableSeperatedIterable {
      return ClickableSeperatedIterable(iterable, clickEvent = clickEvent, dataColor = color)
    }

    fun of(vararg iterable: Any, clickEvent: (Any) -> ClickEvent, color: (Any) -> String): ClickableSeperatedIterable {
      return ClickableSeperatedIterable(iterable.toList(), clickEvent = clickEvent, dataColor = color)
    }
  }
}