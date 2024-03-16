package net.vellity.service.config.configuration

import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface ConfigurationController {
  @GetMapping("/configurations")
  fun getConfiguration(
    @RequestParam("context") context: Context,
    @RequestParam("path") path: String
  ): ResponseEntity<String>

  @PostMapping("/configurations/update")
  fun update(): ResponseEntity<Unit>
}