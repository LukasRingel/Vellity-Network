import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class HttpServerTests {

  @Test
  fun failIfHttpServerNotExists() {
    assertDoesNotThrow { Class.forName("net.vellity.utils.httpserver.HttpServer") }
  }

  @Test
  fun failIfFactoryNotExists() {
    assertDoesNotThrow { Class.forName("net.vellity.utils.httpserver.HttpServerFactory") }
  }

  @Test
  fun failIfConfigurationNotExists() {
    assertDoesNotThrow { Class.forName("net.vellity.utils.httpserver.HttpServerConfiguration") }
  }


}