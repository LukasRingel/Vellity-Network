package net.vellity.utils.mojang

import net.vellity.utils.configuration.fromJson
import java.io.*
import java.net.URL
import java.net.URLConnection
import java.nio.charset.StandardCharsets


class MojangServerRequest<R> (private val url: String, private val type: Class<R>) {
  fun request(): R {
    try {
      val connection = openConnection()
      val inputStream: InputStream? = connection?.getInputStream()
      val streamReader = inputStream?.let { InputStreamReader(it, StandardCharsets.UTF_8) }
      val reader = streamReader?.let { BufferedReader(it) }
      val json = fromJson(reader!!, type)
      close(reader, streamReader, inputStream)
      return json
    } catch (e: IOException) {
      throw RuntimeException(e)
    }

  }

  private fun openConnection(): URLConnection? {
    val connection: URLConnection? = URL(url).openConnection()
    connection?.setRequestProperty("User-Agent", USER_AGENT)
    return connection
  }

  private fun close(vararg closeables: Closeable?) {
    for (closeable in closeables) {
      closeable?.close()
    }
  }

  companion object {
    private const val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0"
  }
}