package net.vellity.service.explorer.registry

import net.vellity.service.explorer.Identity
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DirectServiceRegistryController(private val repository: ServiceRegistryRepository) : ServiceRegistryController {
  override fun getAll(context: Context): ResponseEntity<List<Identity>> {
    return ResponseEntity.ok(repository.getAll(context))
  }

  override fun getAllOfType(type: String, context: Context): ResponseEntity<List<Identity>> {
    return ResponseEntity.ok(repository.getAllOfType(type, context))
  }

  override fun register(type: String, address: String, port: Int, context: Context): ResponseEntity<Identity> {
    return ResponseEntity.ok(repository.register(type, address, port, context))
  }

  override fun updateHearthBeat(type: String, id: String, context: Context, hostname: String, port: Int): ResponseEntity<Unit> {
    repository.updateHearthBeat(type, id, context, hostname, port)
    return ResponseEntity.ok().build()
  }

  override fun unregister(type: String, id: String, context: Context): ResponseEntity<Unit> {
    repository.unregister(type, id, context)
    return ResponseEntity.ok().build()
  }
}