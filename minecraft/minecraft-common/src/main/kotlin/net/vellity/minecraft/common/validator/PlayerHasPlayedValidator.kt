package net.vellity.minecraft.common.validator

import net.vellity.minecraft.common.usercacheClient

class PlayerHasPlayedValidator : Validator<String> {
  override fun isValid(input: String): Boolean {
    val uuidByName = usercacheClient.names().getUuidByName(input).execute()
    if (!uuidByName.isSuccessful) {
      return false
    }
    return uuidByName.body() != null
  }
}