package net.vellity.utils.configuration

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Files

class Configuration<T>(
  private val path: String,
  private val type: Class<T>,
  private val gson: Gson = fallbackGson
) {
  fun getSilent(default: T): T {
    return getSilentOrLoud(true, default)!!
  }

  fun get(): T {

    if (type.getConstructor() != null) {
      return getSilentOrLoud(false, type.getConstructor().newInstance())!!
    }

    return getSilentOrLoud(false, null)!!
  }

  private fun getSilentOrLoud(silent: Boolean, default: T?): T? {
    val file = File(path)

    if (!file.exists()) {
      if (!silent) {
        throw RuntimeException("Configuration file $path does not exist")
      }
      tryCreate(file, default)
    }

    return try {
      gson.fromJson(file.readText(), type)
    } catch (e: Exception) {
      if (!silent) {
        throw RuntimeException("Failed to parse configuration file $path", e)
      }
      return default
    }
  }

  private fun tryCreate(file: File, default: T?) {
    if (!Environment.isDevelopmentEnvironment()) {
      return
    }

    if (!file.exists()) {
      Files.createFile(file.toPath())
    }

    with(BufferedWriter(FileWriter(file))) {
      gson.toJson(default, this)
      close()
    }
  }

  companion object {
    private val fallbackGson = GsonBuilder()
      .serializeNulls()
      .setPrettyPrinting()
      .create()
  }
}