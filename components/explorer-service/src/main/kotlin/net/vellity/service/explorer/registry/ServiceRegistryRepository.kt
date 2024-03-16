package net.vellity.service.explorer.registry

import net.vellity.service.explorer.Identity
import net.vellity.utils.context.Context

interface ServiceRegistryRepository {
  fun getAll(context: Context): List<Identity>

  fun getAllOfType(type: String, context: Context): List<Identity>

  fun register(type: String, hostname: String, port: Int, context: Context): Identity

  fun updateHearthBeat(type: String, id: String, context: Context, hostname: String, port: Int)

  fun unregister(type: String, id: String, context: Context)
}