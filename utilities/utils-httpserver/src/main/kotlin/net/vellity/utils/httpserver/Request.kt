package net.vellity.utils.httpserver

import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange

class Request(private val exchange: HttpExchange, private val parameters: List<Parameter>) {

  fun answer(code: Int) {
    exchange.sendResponseHeaders(code, 0)
    exchange.close()
  }

  fun answer(code: Int, body: String) {
    exchange.sendResponseHeaders(code, body.length.toLong())
    exchange.responseBody.use { it.write(body.toByteArray()) }
    exchange.close()
  }

  fun answer(body: String) {
    answer(200, body)
  }

  fun answer(body: Any) {
    answer(200, gson.toJson(body))
  }

  fun answer(code: Int, body: Any) {
    answer(code, gson.toJson(body))
  }

  fun answerException(body: Exception) {
    val message = body.message!!
    exchange.sendResponseHeaders(500, message.length.toLong())
    exchange.responseBody.use { it.write(message.toByteArray()) }
    exchange.close()
  }

  fun requestBodyContent(): ByteArray {
    return exchange.requestBody.use { it.readBytes() }
  }

  fun <T> requestBody(type: Class<T>): T {
    return gson.fromJson(requestBodyContent().toString(Charsets.UTF_8), type)
  }

  fun hasRequestBody(): Boolean {
    return exchange.requestBody.available() > 0
  }


  fun <T> requiredRequestBody(type: Class<T>): T? {
    if(!hasRequestBody()) {
      answer(400, "Request body is required")
      return null
    }
    return gson.fromJson(requestBodyContent().toString(Charsets.UTF_8), type)
  }

  fun parameter(name: String): String? {
    parameters.forEach { parameter ->
      if (parameter.name == name) {
        return parameter.value
      }
    }
    return null
  }

  fun requiredParameter(name: String): String? {
    parameters.forEach { parameter ->
      if (parameter.name == name) {
        return parameter.value
      }
    }

    answer(400, "Parameter $name is required")
    return null
  }

  fun requestSourceAddress(): String {
    return exchange.remoteAddress.address.hostAddress.toString()
  }

  fun requestSourcePort(): Int {
    return exchange.remoteAddress.port
  }

  companion object {
    private val gson: Gson = Gson()
  }
}