package net.vellity.service.explorer.registry.generator

import org.springframework.stereotype.Component

@Component
class AlphanumericIdGenerator : ServiceIdGenerator {
  override fun generateNewId(type: String): String {
    return "$type-${generateNewAlphaNumericId()}"
  }

  private fun generateNewAlphaNumericId(): String {
    val length = 10
    return (1..length)
      .map { CHARS.random() }
      .joinToString("")
  }

  companion object {
    private const val CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
  }
}