package net.vellity.service.usercache.combined

import java.util.UUID

data class TextureAndName(
  val uuid: UUID,
  val name: String,
  val textureSignature: String,
  val textureValue: String
) {
  companion object {
    fun from(uuid: UUID, name: String, textureSignature: String, textureValue: String) =
      TextureAndName(uuid, name, textureSignature, textureValue)

    fun from(uuid: UUID, name: String) =
      TextureAndName(uuid, name, "", "")
  }
}