package net.vellity.service.explorer.registry.generator

interface ServiceIdGenerator {
  fun generateNewId(type: String): String
}