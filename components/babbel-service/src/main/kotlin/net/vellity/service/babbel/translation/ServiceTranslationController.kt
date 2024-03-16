package net.vellity.service.babbel.translation

import net.vellity.service.babbel.Bundle
import net.vellity.service.babbel.translation.bundle.BundleService
import net.vellity.service.babbel.translation.repository.RepositoryUpdateWorker
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ServiceTranslationController(
  private val bundleService: BundleService,
  private val updateWorker: RepositoryUpdateWorker
) : TranslationController {
  override fun bundle(context: Context): ResponseEntity<Bundle> {
    return ResponseEntity.ok(bundleService.getBundle(context))
  }

  override fun update(): ResponseEntity<Unit> {
    updateWorker.update()
    return ResponseEntity.ok().build()
  }
}