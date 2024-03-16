package net.vellity.minecraft.common.validator

import java.util.UUID

class Validators {
  companion object {
    private val playerHasPlayedValidator = PlayerHasPlayedValidator()
    private val minecraftNameValidator = MinecraftNameValidator()
    private val sessionValidator = SessionValidator()

    fun hasPlayedBefore(name: String): Boolean {
      return playerHasPlayedValidator.isValid(name)
    }

    fun isValidMinecraftName(name: String): Boolean {
      return minecraftNameValidator.isValid(name)
    }

    fun isOnline(uuid: UUID): Boolean {
      return sessionValidator.isValid(uuid)
    }
  }
}