package net.vellity.minecraft.velocity.login.punish

import com.velocitypowered.api.event.ResultedEvent
import com.velocitypowered.api.event.connection.LoginEvent
import com.velocitypowered.api.proxy.Player
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.punishClient
import net.vellity.minecraft.common.translations.TranslationRepository
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.minecraft.common.translations.transformer.date.FormattedDateTime
import net.vellity.minecraft.velocity.extensions.BabbelPlayerExtensions.Companion.translate
import net.vellity.service.punish.punishments.Punishment
import net.vellity.service.punish.punishments.PunishmentRequestDetails
import net.vellity.service.punish.punishments.PunishmentType
import retrofit2.Response
import java.util.*

class DirectPunishmentLoginCheck : PunishmentLoginCheck {
  override fun processPunishmentLoginCheck(event: LoginEvent) {
    val response = requestActiveNetworkBanPunishments(event)

    if (errorAtRequest(response)) {
      event.result = errorAtRequestEventResult(event.player)
      return
    }

    if (hasNoActiveNetworkBanPunishment(response)) {
      return
    }

    event.result = denyLoginEventResult(response.body()!!.first())
  }

  private fun denyLoginEventResult(punishment: Punishment): ResultedEvent.ComponentResult =
    ResultedEvent.ComponentResult.denied(
      MiniMessageFormat.format(
        TranslationRepository.get(
          "commons-punish-event-login-screen",
          Locale.US,
          arrayOf(
            "until",
            FormattedDateTime.timeAndDate(punishment.activeUntil),
            "reason",
            punishment.reason
          )
        )
      )
    )

  private fun errorAtRequestEventResult(player: Player): ResultedEvent.ComponentResult =
    ResultedEvent.ComponentResult.denied(
      player.translate(
        "commons-punish-event-login-error"
      )
    )

  private fun hasNoActiveNetworkBanPunishment(response: Response<List<Punishment>>) =
    response.body() == null || response.body()!!.isEmpty()

  private fun errorAtRequest(response: Response<List<Punishment>>) =
    !response.isSuccessful

  private fun requestActiveNetworkBanPunishments(event: LoginEvent): Response<List<Punishment>> =
    punishClient.punishments()
      .getActivePunishmentsOfType(
        context,
        event.player.uniqueId,
        PunishmentType.BAN_NETWORK,
        PunishmentRequestDetails.WITHOUT
      )
      .execute()
}