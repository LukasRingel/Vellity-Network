package net.vellity.service.config.flags

import net.vellity.service.config.FeatureFlag
import net.vellity.utils.context.Context
import net.vellity.utils.configuration.lifetimeDate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class ServiceFeatureFlagsController(private val featureFlagsService: FeatureFlagsService) : FeatureFlagsController {
  override fun getFlags(context: Context): ResponseEntity<List<FeatureFlag>> {
    return ResponseEntity.ok(featureFlagsService.getFlags(context))
  }

  override fun getFlag(context: Context, name: String): ResponseEntity<FeatureFlag> {
    return ResponseEntity.ok(featureFlagsService.getFlag(context, name))
  }

  override fun isFlagActive(context: Context, name: String, default: Boolean): ResponseEntity<Boolean> {
    return ResponseEntity.ok(featureFlagsService.isFlagActive(context, name, default))
  }

  override fun createFlag(
    context: Context,
    name: String,
    enabled: Boolean,
    activeUntil: String
  ): ResponseEntity<FeatureFlag> {
    return ResponseEntity.ok(featureFlagsService.createFlag(context, name, enabled, stringToInstant(activeUntil)))
  }

  override fun updateFlag(
    context: Context,
    name: String,
    enabled: Boolean,
    activeUntil: String
  ): ResponseEntity<FeatureFlag> {
    return ResponseEntity.ok(featureFlagsService.updateFlag(context, name, enabled, stringToInstant(activeUntil)))
  }

  private fun stringToInstant(str: String?): Instant {
    if (str.isNullOrEmpty()) {
      return lifetimeDate
    }
    return if (isNumeric(str)) {
      Instant.ofEpochMilli(str.toLong())
    } else {
      Instant.parse(str)
    }
  }

  private fun isNumeric(str: String): Boolean {
    val sz = str.length
    for (i in 0 until sz) {
      if (!Character.isDigit(str[i])) {
        return false
      }
    }
    return true
  }
}