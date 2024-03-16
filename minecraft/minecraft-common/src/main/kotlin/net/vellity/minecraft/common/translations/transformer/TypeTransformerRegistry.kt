package net.vellity.minecraft.common.translations.transformer

import net.vellity.minecraft.common.translations.transformer.data.ClickableSeperatedIterableTransformer
import net.vellity.minecraft.common.translations.transformer.data.LocaleTransformer
import net.vellity.minecraft.common.translations.transformer.data.SeperatedIterableTransformer
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTimeTransformer
import net.vellity.minecraft.common.translations.transformer.player.NotificationTypeTransformer
import net.vellity.minecraft.common.translations.transformer.player.PermissionGroupTypeTransformer
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerNameTransformer
import net.vellity.minecraft.common.translations.transformer.player.color.PrefixedPlayerNameTransformer
import net.vellity.minecraft.common.translations.transformer.player.component.AdventureComponentTransformer
import net.vellity.minecraft.common.translations.transformer.player.currency.CurrencyTransformer
import net.vellity.minecraft.common.translations.transformer.primitives.*
import net.vellity.minecraft.common.translations.transformer.punish.PunishmentReasonTypeTransformer
import net.vellity.minecraft.common.translations.transformer.server.ServerGroupDisplayTransformer

object TypeTransformerRegistry {
  private val converters = mutableListOf<TypeTransformer<*>>(
    BooleanTransformer(),
    ByteTransformer(),
    DoubleTransformer(),
    FloatTransformer(),
    IntegerTransformer(),
    LongTransformer(),
    ShortTransformer(),
    ServerGroupDisplayTransformer(),
    FormattedDateTimeTransformer(),
    ColoredPlayerNameTransformer(),
    PrefixedPlayerNameTransformer(),
    PunishmentReasonTypeTransformer(),
    AdventureComponentTransformer(),
    SeperatedIterableTransformer(),
    ClickableSeperatedIterableTransformer(),
    CurrencyTransformer(),
    LocaleTransformer(),
    NotificationTypeTransformer(),
    PermissionGroupTypeTransformer()
  )

  fun register(converter: TypeTransformer<*>) =
    converters.add(converter)

  fun getConverter(clazz: Class<*>): TypeTransformer<*>? =
    converters.firstOrNull { it.supports(clazz) }

  fun converters(): List<TypeTransformer<*>> =
    converters
}