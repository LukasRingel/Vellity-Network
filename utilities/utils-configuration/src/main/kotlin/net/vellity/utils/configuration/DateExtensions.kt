package net.vellity.utils.configuration

import java.time.Instant

// the 2038/1/19 at 00:00:00 to indicate lifetime (mysql datetime max value)
val lifetimeDate: Instant = Instant.ofEpochMilli(2147468400000)
const val lifetimeDateMillis = 2147468400000

fun isLifetime(instant: Instant): Boolean {
  return instant.toEpochMilli() >= lifetimeDateMillis
}