package net.vellity.minecraft.common.translations.transformer.data

import net.kyori.adventure.text.event.ClickEvent
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.translations.transformer.Transformation
import net.vellity.minecraft.common.translations.transformer.TypeTransformer
import java.util.*

class ClickableSeperatedIterableTransformer : TypeTransformer<ClickableSeperatedIterable> {
  override fun toString(instance: Any, locale: Locale): String {
    val seperatedIterable = instance as ClickableSeperatedIterable

    if (seperatedIterable.iterable.count() == 0) {
      return ""
    }

    if (seperatedIterable.iterable.count() == 1) {
      return appendClickEventToText(seperatedIterable.iterable.first(), locale, seperatedIterable.clickEvent)
    }

    return seperatedIterable.iterable.joinToString(seperatedIterable.seperator) {
      seperatedIterable.decorate(it, appendClickEventToText(it, locale, seperatedIterable.clickEvent))
    }
  }

  private fun appendClickEventToText(element: Any, locale: Locale, clickEvent: (Any) -> ClickEvent): String =
    MiniMessageFormat.componentToString(
      MiniMessageFormat.format(Transformation.transform(element, locale))
        .clickEvent(clickEvent(element))
    )


  override fun fallback(locale: Locale): String =
    "N/A"

  override fun supports(clazz: Class<*>): Boolean =
    ClickableSeperatedIterable::class.java.isAssignableFrom(clazz)
}