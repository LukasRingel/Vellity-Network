package net.vellity.service.config.configuration

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceConfigurationController(private val configurationService: ConfigurationService) : ConfigurationController {
  override fun getConfiguration(context: Context, path: String): ResponseEntity<String> {
    return ResponseEntity.ok(configurationService.getConfiguration(context, path))
  }

  override fun update(): ResponseEntity<Unit> {
    configurationService.update()
    return ResponseEntity.ok().build()
  }
}