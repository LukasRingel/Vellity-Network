package net.vellity.utils.httpserver.spring

import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
  @ResponseBody
  @ExceptionHandler(Exception::class)
  fun handleRestExceptions(e: Exception, request: WebRequest): ErrorResponse {
    return ErrorResponse.builder(
      e,
      HttpStatus.INTERNAL_SERVER_ERROR,
      if (e.message != null) e.message!! else "Internal Server Error"
    ).type(URI.create(e.javaClass.name)).build()
  }
}