package net.vellity.minecraft.common.validator

class MinecraftNameValidator: Validator<String> {
  override fun isValid(input: String): Boolean {
    return input.matches("^[a-zA-Z0-9_]{2,16}\$".toRegex())
  }

  override fun errorMessage(): String {
    return "commons-input-validator-minecraft-name"
  }
}