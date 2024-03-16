package net.vellity.service.explorer.registry

import net.vellity.service.explorer.Identity
import net.vellity.utils.context.Context

class IdentityMapper {
  companion object {
    fun toMap(identity: Identity): Map<String, String> {
      return mapOf(
        "id" to identity.id,
        "context" to identity.context.name,
        "type" to identity.type,
        "hostname" to identity.hostname,
        "port" to identity.port.toString()
      )
    }

    fun fromMap(map: Map<String, String>): Identity {
      return Identity(
        map["id"]!!,
        Context.valueOf(map["context"]!!),
        map["type"]!!,
        map["hostname"]!!,
        map["port"]!!.toInt()
      )
    }
  }
}