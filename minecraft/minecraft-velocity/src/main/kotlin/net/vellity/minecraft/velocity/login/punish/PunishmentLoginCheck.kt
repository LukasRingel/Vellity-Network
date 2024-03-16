package net.vellity.minecraft.velocity.login.punish

import com.velocitypowered.api.event.connection.LoginEvent

interface PunishmentLoginCheck {
  fun processPunishmentLoginCheck(event: LoginEvent)
}