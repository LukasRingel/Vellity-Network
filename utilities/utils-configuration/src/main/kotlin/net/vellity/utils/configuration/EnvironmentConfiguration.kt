package net.vellity.utils.configuration

data class EnvironmentConfiguration(
  val vars: Map<String, String>
) {
  fun get(key: String): String? {
    return vars[key]
  }
}