package net.vellity.minecraft.common.validator

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.usercacheClient
import java.util.UUID

class SessionValidator : Validator<UUID> {
  override fun isValid(input: UUID): Boolean {
    return usercacheClient.sessions()
      .getCurrentSession(context, input)
      .execute()
      .isSuccessful
  }
}