package net.vellity.service.config.flags

import net.vellity.service.config.FeatureFlag
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface FeatureFlagsController {
  @GetMapping("/flags")
  fun getFlags(@RequestParam("context") context: Context): ResponseEntity<List<FeatureFlag>>

  @GetMapping("/flags/id")
  fun getFlag(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String
  ): ResponseEntity<FeatureFlag>

  @GetMapping("/flags/active")
  fun isFlagActive(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String,
    @RequestParam("default") default: Boolean
  ): ResponseEntity<Boolean>

  @PostMapping("/flags")
  fun createFlag(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String,
    @RequestParam("enabled") enabled: Boolean,
    @RequestParam("activeUntil") activeUntil: String
  ): ResponseEntity<FeatureFlag>

  @PutMapping("/flags")
  fun updateFlag(
    @RequestParam("context") context: Context,
    @RequestParam("name") name: String,
    @RequestParam("enabled") enabled: Boolean,
    @RequestParam("activeUntil") activeUntil: String
  ): ResponseEntity<FeatureFlag>
}