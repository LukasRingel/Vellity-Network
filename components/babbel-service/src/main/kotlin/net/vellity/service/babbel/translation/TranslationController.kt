package net.vellity.service.babbel.translation

import net.vellity.service.babbel.Bundle
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

interface TranslationController {
  @GetMapping("/translations")
  fun bundle(@RequestParam("context") context: Context): ResponseEntity<Bundle>

  @PostMapping("/translations/update")
  fun update(): ResponseEntity<Unit>
}