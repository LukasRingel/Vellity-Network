package net.vellity.minecraft.common.translations.transformer.date

import net.vellity.minecraft.common.translations.TranslationRepository
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

class FormattedDateTime(private val instant: Instant, private val length: Length) {
  fun getFormattedDateTime(locale: Locale): String {
    val formatKey = when (length) {
      Length.ONLY_TIME -> "commons-format-time-time"
      Length.DATE -> "commons-format-time-date"
      Length.TIME_AND_DATE -> "commons-format-time-time-and-date"
    }
    val dateFormat = SimpleDateFormat(TranslationRepository.get(formatKey, locale))
    dateFormat.timeZone = getTimeZoneByLocale(locale)
    return dateFormat.format(Date(instant.toEpochMilli()))
  }

  private fun getTimeZoneByLocale(locale: Locale): TimeZone {
    return TimeZone.getTimeZone(TranslationRepository.get("commons-format-time-zone", locale))
  }

  enum class Length {
    ONLY_TIME,
    TIME_AND_DATE,
    DATE
  }

  companion object {
    fun onlyTime(instant: Instant): FormattedDateTime {
      return FormattedDateTime(instant, Length.ONLY_TIME)
    }

    fun timeAndDate(instant: Instant): FormattedDateTime {
      return FormattedDateTime(instant, Length.TIME_AND_DATE)
    }

    fun date(instant: Instant): FormattedDateTime {
      return FormattedDateTime(instant, Length.DATE)
    }

    fun auto(instant: Instant): FormattedDateTime {
      val diff = instant.epochSecond - Clock.systemUTC().instant().epochSecond

      if (diff < ONE_DAY_IN_SECONDS) {
        return onlyTime(instant)
      }

      if (diff < ONE_WEEK_IN_SECONDS) {
        return timeAndDate(instant)
      }

      return date(instant)
    }

    private val ONE_WEEK_IN_SECONDS = TimeUnit.DAYS.toSeconds(7)
    private val ONE_DAY_IN_SECONDS = TimeUnit.DAYS.toSeconds(1)
  }
}