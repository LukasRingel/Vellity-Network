package net.vellity.minecraft.common.translations.alert

interface TranslatableAlert<P> {
  fun send(player: P, vararg args: Any?)

  fun broadcast(replacements: (P) -> Array<Any?>)
}