package net.vellity.minecraft.velocity.login.maintenance

import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.connection.LoginEvent
import net.vellity.minecraft.common.configClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.velocity.extensions.BabbelPlayerExtensions.Companion.translate
import net.vellity.minecraft.velocity.extensions.hasAccess
import retrofit2.Response
import net.vellity.minecraft.common.flags.NetworkFlags.Companion.MAINTENANCE as MAINTENANCE_FLAG
import net.vellity.minecraft.common.permissions.NetworkPermissions.Companion.MAINTENANCE_ACCESS as MAINTENANCE_ACCESS_PERMISSION

class DirectMaintenanceLoginCheck() : MaintenanceLoginCheck {
  override fun processMaintenanceLoginCheck(event: LoginEvent) {
    val response = requestMaintenanceModeCheck()

    if (errorAtRequest(response)) {
      event.result = errorAtRequestEventResult(event)
      return
    }

    if (maintenanceModeNotEnabled(response)) {
      return
    }

    if (hasAccessToJoinWhileMaintenance(event)) {
      return
    }

    event.result = denyLoginEventResult(event)
  }

  private fun denyLoginEventResult(event: LoginEvent): ResultedEvent.ComponentResult? =
    ResultedEvent.ComponentResult.denied(event.player.translate("commons-maintenance-login-screen"))

  private fun errorAtRequestEventResult(event: LoginEvent): ResultedEvent.ComponentResult? =
    ResultedEvent.ComponentResult.denied(event.player.translate("commons-maintenance-login-error"))

  private fun errorAtRequest(response: Response<Boolean>) =
    !response.isSuccessful

  private fun maintenanceModeNotEnabled(response: Response<Boolean>) =
    !response.body()!!

  private fun requestMaintenanceModeCheck(): Response<Boolean> =
    configClient.featureFlags().isFlagActive(context, MAINTENANCE_FLAG, true).execute()

  private fun hasAccessToJoinWhileMaintenance(event: LoginEvent) =
    event.player.hasAccess(MAINTENANCE_ACCESS_PERMISSION)
}