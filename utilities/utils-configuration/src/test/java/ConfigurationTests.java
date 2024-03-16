import net.vellity.utils.configuration.ConfigurationFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTests {

  @Test
  void failIfConfigurationClassDoesNotExists() {
    assertDoesNotThrow(() -> {
      Class.forName("net.vellity.utils.configuration.Configuration");
    });
  }

  @Test
  void failIfConfigurationFactoryDoesNotExists() {
    assertDoesNotThrow(() -> {
      Class.forName("net.vellity.utils.configuration.ConfigurationFactory");
    });
  }

  @Test
  void failIfConfigurationPathIsInvalid() {
    ConfigurationFactory.INSTANCE.updatePath("config");
    assertEquals("config/", ConfigurationFactory.INSTANCE.currentPath());
  }

  @Test
  void failIfFileNotExistsAndNoException() {
    assertThrows(RuntimeException.class, () ->
      ConfigurationFactory.INSTANCE.create("test.json", TestObject.class)
    );
  }

  @Test
  void failIfWrongValuesFromFile() {
    ConfigurationFactory.INSTANCE.updatePath("src/test/resources/");
    var configuration = ConfigurationFactory.INSTANCE.create(
      "testconfig.json",
      TestObject.class
    );
    assertTrue(configuration.get().getName().equals("lukas") &&
      configuration.get().getAge() == 25);
  }

}