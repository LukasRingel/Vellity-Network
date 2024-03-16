package net.vellity.minecraft.velocity.login.events

import com.velocitypowered.api.event.Continuation
import com.velocitypowered.api.event.EventManager
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.connection.LoginEvent
import net.vellity.minecraft.common.globalThreadPool
import net.vellity.minecraft.velocity.login.maintenance.MaintenanceLoginCheck
import net.vellity.minecraft.velocity.login.punish.PunishmentLoginCheck

class VelocityLoginEventListener(
  private val maintenanceLoginCheck: MaintenanceLoginCheck,
  private val punishmentLoginCheck: PunishmentLoginCheck,
  private val eventManager: EventManager
) {
  @Subscribe
  private fun onLogin(event: LoginEvent, continuation: Continuation) {
    globalThreadPool.submit {
      maintenanceLoginCheck.processMaintenanceLoginCheck(event)
      if (loginHasBeenDenied(event)) {
        continuation.resume()
        return@submit
      }

      punishmentLoginCheck.processPunishmentLoginCheck(event)
      if (loginHasBeenDenied(event)) {
        continuation.resume()
        return@submit
      }

      continuation.resume()
      eventManager.fireAndForget(NetworkLoginEvent(event.player))
    }
  }

  private fun loginHasBeenDenied(event: LoginEvent) =
    !event.result.isAllowed
}