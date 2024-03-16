package net.vellity.minecraft.common.spigot.commands.economy

import net.vellity.minecraft.common.economyClient
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.service.economy.currency.Currency
import org.bukkit.entity.Player

class EconomyCommand(private val currency: Currency) : PlayerCommand(
  currency.name.lowercase(),
  "Shows your current amount of " + currency.name.lowercase()
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    val balanceResponse = economyClient.balances().getBalance(player.uniqueId, currency.id).execute()

    if (!balanceResponse.isSuccessful) {
      player.sendTranslatedMessage("commons-command-economy-own-error")
      return
    }

    player.sendTranslatedMessage(
      "commons-command-economy-own-success",
      "balance",
      balanceResponse.body()!!.balance,
      "currency",
      currency
    )
  }

  companion object {
    fun buildEconomyCommands(): Array<PlayerCommand> {
      val listResponse = economyClient.currencies().getCurrencies().execute()
      if(!listResponse.isSuccessful) {
        return emptyArray()
      }
      return listResponse.body()!!.map { EconomyCommand(it) }.toTypedArray()
    }
  }
}