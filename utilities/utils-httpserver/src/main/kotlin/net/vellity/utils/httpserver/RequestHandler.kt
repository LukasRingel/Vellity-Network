package net.vellity.utils.httpserver

@FunctionalInterface
fun interface RequestHandler {
  fun handle(request: Request)
}