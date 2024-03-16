package net.vellity.minecraft.common.spigot.item.textures

import net.vellity.minecraft.common.usercacheClient
import java.util.*

class UserCacheStrategy(private val playerUuid: UUID) : TextureStrategy {
  override fun textureValue(): String {
    if (Thread.currentThread().name.equals("Server thread", true)) {
      throw IllegalStateException("Cannot download texture on main thread")
    }
    val textureResponse = usercacheClient.textures().getTextureByUuid(playerUuid).execute()
    if (!textureResponse.isSuccessful) {
      return FALLBACK
    }
    return textureResponse.body()!!.value
  }

  companion object {
    private const val FALLBACK = "ewogICJ0aW1lc3RhbXAiIDogMTYyMjc1MDc3ODY4NywKICAicHJvZmlsZUlkIiA6ICJmOTJiZDcxNGE0" +
      "NmE0MmQ0OTQ1ZjdiMGNmNzExMDllNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlrb2xpbmsiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB" +
      "0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubm" +
      "V0L3RleHR1cmUvZWNmYWU4NjM4ZmNkYzRlNDk2ODdmMjFmZGEwN2EzOThmYjc0Mjk4NjBhZDU5NzAwNjAxYTA0OGI5N2ExODFmNCIKICAgI" +
      "H0KICB9Cn0="
  }
}