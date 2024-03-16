package net.vellity.minecraft.common.spigot.item.textures

class ProvidedStrategy(
  private val textureValue: String = "ewogICJ0aW1lc3RhbXAiIDogMTYyMjc1MDc3ODY4NywKICAicHJvZmlsZUlkIiA6ICJmOTJiZDcxNGE0" +
    "NmE0MmQ0OTQ1ZjdiMGNmNzExMDllNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlrb2xpbmsiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB" +
    "0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubm" +
    "V0L3RleHR1cmUvZWNmYWU4NjM4ZmNkYzRlNDk2ODdmMjFmZGEwN2EzOThmYjc0Mjk4NjBhZDU5NzAwNjAxYTA0OGI5N2ExODFmNCIKICAgI" +
    "H0KICB9Cn0="
) : TextureStrategy {
  override fun textureValue(): String = textureValue
}