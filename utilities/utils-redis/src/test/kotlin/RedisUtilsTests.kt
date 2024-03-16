import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class RedisUtilsTests {

  @Test
  fun failIfFactoryNotExists() {
    assertDoesNotThrow { Class.forName("net.vellity.utils.redis.RedisConnectionFactory") }
  }

}