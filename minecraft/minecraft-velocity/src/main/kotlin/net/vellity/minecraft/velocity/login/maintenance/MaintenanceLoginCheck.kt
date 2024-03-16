package net.vellity.minecraft.velocity.login.maintenance

import com.velocitypowered.api.event.connection.LoginEvent

interface MaintenanceLoginCheck {
  fun processMaintenanceLoginCheck(event: LoginEvent)
}