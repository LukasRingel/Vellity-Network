package net.vellity.utils.mojang.model

data class SessionServerResponse(
  val id: String,
  val name: String,
  val properties: List<Property>
)