package net.vellity.service.explorer.registry

import net.vellity.service.explorer.Identity
import net.vellity.utils.context.Context
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

interface ServiceRegistryController {
  @GetMapping("/registry")
  fun getAll(@RequestParam("context") context: Context): ResponseEntity<List<Identity>>

  @GetMapping("/registry/filter")
  fun getAllOfType(
    @RequestParam("type") type: String,
    @RequestParam("context") context: Context
  ): ResponseEntity<List<Identity>>

  @PostMapping("/registry")
  fun register(
    @RequestParam("type") type: String,
    @RequestParam("address") address: String,
    @RequestParam("port") port: Int,
    @RequestParam("context") context: Context
  ): ResponseEntity<Identity>

  @PutMapping("/registry")
  fun updateHearthBeat(
    @RequestParam("type") type: String,
    @RequestParam("id") id: String,
    @RequestParam("context") context: Context,
    @RequestParam("address") address: String,
    @RequestParam("port") port: Int,
  ): ResponseEntity<Unit>

  @DeleteMapping("/registry")
  fun unregister(
    @RequestParam("type") type: String,
    @RequestParam("id") id: String,
    @RequestParam("context") context: Context
  ): ResponseEntity<Unit>
}