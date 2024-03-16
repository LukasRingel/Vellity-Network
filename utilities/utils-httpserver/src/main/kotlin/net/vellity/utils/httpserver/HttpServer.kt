package net.vellity.utils.httpserver

import com.google.common.util.concurrent.ThreadFactoryBuilder
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors
import java.util.logging.Logger

class HttpServer(
  private val configuration: HttpServerConfiguration,
  private val logger: Logger? = null
) {

  private val server: HttpServer = HttpServer.create(InetSocketAddress(configuration.port), 0)

  fun withPublicAccess(type: RequestType, route: String, handler: RequestHandler) {
    withPublicAccess(type, route) { handler.handle(it) }
  }

  fun withPublicAccess(type: RequestType, route: String, handler: (Request) -> Unit) {
    logger?.info("Bound $type $route without access restriction")
    server.createContext(route) { httpExchange ->

      if (httpExchange.requestMethod != type.name) {
        httpExchange.sendResponseHeaders(405, 0)
        httpExchange.close()
        return@createContext
      }

      processRequest(httpExchange, handler)
    }
  }

  fun withAccessRestriction(type: RequestType, route: String, handler: (Request) -> Unit) {
    logger?.info("Bound $type $route with access restriction")
    server.createContext(route) { httpExchange ->

      if (httpExchange.requestMethod != type.name) {
        httpExchange.sendResponseHeaders(405, 0)
        httpExchange.close()
        return@createContext
      }

      if (!httpExchange.requestHeaders.containsKey("API-KEY")) {
        httpExchange.sendResponseHeaders(401, 0)
        httpExchange.close()
        return@createContext
      }

      val apiKey = httpExchange.requestHeaders["API-KEY"]?.first()

      if (apiKey != configuration.authKey) {
        httpExchange.sendResponseHeaders(401, 0)
        httpExchange.close()
        return@createContext
      }

      processRequest(httpExchange, handler)
    }
  }

  fun withAccessRestriction(type: RequestType, route: String, handler: RequestHandler) {
    withAccessRestriction(type, route) { handler.handle(it) }
  }

  fun withPublicAccess(route: String, handler: (Request) -> Unit) {
    withPublicAccess(RequestType.GET, route, handler)
  }

  fun withPublicAccess(route: String, handler: RequestHandler) {
    withPublicAccess(RequestType.GET, route, handler)
  }

  fun withAccessRestriction(route: String, handler: (Request) -> Unit) {
    withAccessRestriction(RequestType.GET, route, handler)
  }

  fun withAccessRestriction(route: String, handler: RequestHandler) {
    withAccessRestriction(RequestType.GET, route, handler)
  }

  private fun processRequest(httpExchange: HttpExchange, handler: (Request) -> Unit) {
    threadPoolExecutor.submit {
      val startTime = System.currentTimeMillis()
      handler(Request(httpExchange, findParameters(httpExchange)))
      val neededTime = System.currentTimeMillis() - startTime

      val requestUrl = httpExchange.requestURI.toString()
      val displayUrl = if (requestUrl.contains("?"))
        requestUrl.split("?")[0] else
        requestUrl

      logger?.info("Processed $displayUrl in $neededTime ms")
    }
  }

  private fun findParameters(httpExchange: HttpExchange): List<Parameter> {
    val parameters = mutableListOf<Parameter>()

    httpExchange.requestURI.query?.split("&")?.forEach { parameter ->
      val (name, value) = parameter.split("=")
      parameters.add(Parameter(name, value))
    }

    return parameters
  }

  fun start() {
    server.start()
    logger?.info("Http-Server started on port ${configuration.port}")
  }

  fun stop() {
    server.stop(0)
    logger?.info("Http-Server stopped")
  }

  companion object {
    private val threadPoolExecutor = Executors.newCachedThreadPool(
      ThreadFactoryBuilder()
        .setNameFormat("http-exec-%d")
        .build()
    )
  }
}