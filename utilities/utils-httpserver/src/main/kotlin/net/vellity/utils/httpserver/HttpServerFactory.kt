package net.vellity.utils.httpserver

import java.util.logging.Logger

object HttpServerFactory {

  fun createHttpServer(configuration: HttpServerConfiguration): HttpServer {
    return HttpServer(configuration)
  }

  fun createHttpServer(configuration: HttpServerConfiguration, logger: Logger): HttpServer {
    return HttpServer(configuration, logger)
  }

  fun createHttpServer(port: Int): HttpServer {
    return createHttpServer(port, "")
  }

  fun createHttpServer(port: Int, logger: Logger): HttpServer {
    return createHttpServer(port, "", logger)
  }

  fun createHttpServer(port: Int, apiKey: String): HttpServer {
    return createHttpServer(HttpServerConfiguration(port, apiKey))
  }

  fun createHttpServer(port: Int, apiKey: String, logger: Logger): HttpServer {
    return HttpServer(HttpServerConfiguration(port, apiKey), logger)
  }

}