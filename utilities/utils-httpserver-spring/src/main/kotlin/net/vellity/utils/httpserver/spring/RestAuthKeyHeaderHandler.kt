package net.vellity.utils.httpserver.spring

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.vellity.utils.configuration.Environment
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.GenericFilterBean

@Configuration
open class RestAuthKeyHeaderHandler(private val ignoredRoutes: List<String> = listOf()) : GenericFilterBean() {
  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    if (request !is HttpServletRequest) {
      return
    }

    if (ignoredRoutes.contains(request.requestURI)) {
      chain?.doFilter(request, response)
      return
    }

    request.getHeader(AUTH_KEY_HEADER)?.let { authKey ->
      if (authKey != AUTH_KEY) {
        unauthorized(response)
        return
      }
      authorized(request, response, chain)
    } ?: run {
      unauthorized(response)
    }
  }

  private fun authorized(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    chain?.doFilter(request, response)
  }

  private fun unauthorized(response: ServletResponse?) {
    (response as HttpServletResponse).sendError(
      HttpServletResponse.SC_UNAUTHORIZED,
      "Unauthorized"
    )
  }

  companion object {
    private const val AUTH_KEY_HEADER = "API-KEY"
    private val AUTH_KEY = Environment.getOrDefault("HTTP_SERVER_API_KEY", "123456789")
  }
}