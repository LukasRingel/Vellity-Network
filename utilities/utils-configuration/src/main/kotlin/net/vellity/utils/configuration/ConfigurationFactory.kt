package net.vellity.utils.configuration

import com.google.gson.Gson

object ConfigurationFactory {

  private var path: String = ""

  fun currentPath(): String {
    return path
  }

  fun updatePath(tryNewPath: String) {
    path = if (tryNewPath.endsWith("/")) {
      tryNewPath
    } else {
      "$tryNewPath/"
    }
  }

  fun <T> create(pathSuffix: String, type: Class<T>): Configuration<T> {
    return Configuration(buildPath(pathSuffix), type)
  }

  fun <T> create(pathSuffix: String, type: Class<T>, gson: Gson): Configuration<T> {
    return Configuration(buildPath(pathSuffix), type, gson)
  }

  fun <T> createAndGet(pathSuffix: String, type: Class<T>): T {
    return Configuration(buildPath(pathSuffix), type).get()
  }

  fun <T> createAndGet(pathSuffix: String, type: Class<T>, gson: Gson): T {
    return Configuration(buildPath(pathSuffix), type, gson).get()
  }

  fun <T> createAndGetSilent(pathSuffix: String, type: Class<T>, default: T): T {
    return Configuration(buildPath(pathSuffix), type).getSilent(default)
  }

  fun <T> createAndGetSilent(pathSuffix: String, type: Class<T>, gson: Gson, default: T): T {
    return Configuration(buildPath(pathSuffix), type, gson).getSilent(default)
  }

  fun buildPath(pathSuffix: String): String {
    if (!pathSuffix.endsWith(".json")) {
      return "$path$pathSuffix.json"
    }
    return path + pathSuffix
  }
}