package net.vellity.service.babbel.user

import net.vellity.utils.context.Context
import java.util.*

interface UserRepository {
  fun localeOf(context: Context, playerId: UUID): Locale

  fun updateLocale(context: Context, playerId: UUID, locale: Locale)
}