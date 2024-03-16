package net.vellity.utils.configuration

object Environment {

  private var localDevelopmentMode = detectLocalDevelopmentMode()

  private val configuration: EnvironmentConfiguration = ConfigurationFactory.createAndGetSilent(
    findPathForFile() + "environment",
    EnvironmentConfiguration::class.java,
    EnvironmentConfiguration(mutableMapOf())
  )

  fun get(key: String): String {
    if (System.getenv().containsKey(key)) {
      return System.getenv(key)
    }

    return configuration.get(key)!!
  }

  fun getOrDefault(key: String, default: String): String {
    if (System.getenv().containsKey(key)) {
      return System.getenv(key)
    }

    if (configuration == null) {
      return default
    }

    if (configuration.vars.containsKey(key)) {
      return configuration.get(key)!!
    }

    return default
  }

  fun getAsInt(key: String): Int {
    return get(key).toInt()
  }

  fun getAsIntOrDefault(key: String, default: Int): Int {
    return getOrDefault(key, default.toString()).toInt()
  }

  fun getAsLong(key: String): Long {
    return get(key).toLong()
  }

  fun getAsLongOrDefault(key: String, default: Int): Long {
    return getOrDefault(key, default.toString()).toLong()
  }

  fun getAsBoolean(key: String): Boolean {
    return get(key).toBoolean()
  }

  fun getAsBooleanOrDefault(key: String, default: Boolean): Boolean {
    return getOrDefault(key, default.toString()).toBoolean()
  }

  fun isLocalDevelopment(): Boolean {
    return localDevelopmentMode
  }

  fun onLocalDevelopmentEnvironment(task: () -> Unit) {
    if (isLocalDevelopment()) {
      task()
    }
  }

  fun notOnLocalDevelopmentEnvironment(task: () -> Unit) {
    if (!isLocalDevelopment()) {
      task()
    }
  }

  private fun findPathForFile(): String {
    if (System.getenv().containsKey("ENVIRONMENT_FILE_PATH")) {
      return System.getenv("ENVIRONMENT_FILE_PATH")
    }
    return ""
  }

  fun isDevelopmentEnvironment(): Boolean {
    return getOrDefault("ENVIRONMENT", "prod").lowercase().contains("develop")
  }

  private fun detectLocalDevelopmentMode(): Boolean {
    // we assume that we never run a production server on windows
    if (System.getProperty("os.name").lowercase().contains("windows")) {
      return true
    }

    return getOrDefault("ENVIRONMENT", "prod").lowercase() == "local-development"
  }
}