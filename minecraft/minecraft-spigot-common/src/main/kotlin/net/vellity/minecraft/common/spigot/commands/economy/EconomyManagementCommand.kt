package net.vellity.minecraft.common.spigot.commands.economy

import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.economyClient
import net.vellity.minecraft.common.permissions.NetworkPermissions
import net.vellity.minecraft.common.spigot.commands.api.PlayerCommand
import net.vellity.minecraft.common.spigot.extensions.PlayerAccessExtensions.hasAccess
import net.vellity.minecraft.common.spigot.extensions.PlayerBabbelExtensions.sendTranslatedMessage
import net.vellity.minecraft.common.translations.transformer.data.SeperatedIterable
import net.vellity.minecraft.common.translations.transformer.player.color.ColoredPlayerName
import net.vellity.minecraft.common.usercacheClient
import net.vellity.service.economy.currency.Currency
import net.vellity.service.economy.transaction.Action
import net.vellity.service.economy.transaction.blueprint.TransactionCreateBlueprint
import org.bukkit.entity.Player

class EconomyManagementCommand : PlayerCommand(
  "economy",
  "Economy management commands"
) {
  override fun executeCommand(player: Player, args: Array<out String>) {
    // /economy view [player] [economy]
    if (args.size == 3 && args[0].equals("view", true) && hasAccessToViewEconomyData(player)) {
      val playerName = args[1]
      val currencyName = args[2]
      val currenciesForContext = currenciesForContext(player)

      if (targetCurrencyDoesNotExists(currenciesForContext, currencyName)) {
        player.sendTranslatedMessage(
          "commons-command-economy-error-currency",
          "currency",
          currencyName
        )
        return
      }

      val currency = findCurrencyWithName(currenciesForContext, currencyName)!!

      val uuidResponse = usercacheClient.names().getUuidByName(playerName).execute()
      if (!uuidResponse.isSuccessful) {
        player.sendTranslatedMessage(
          "commons-command-economy-error-player",
          "code",
          uuidResponse.code()
        )
        return
      }

      val balanceRequest = economyClient.balances().getBalance(uuidResponse.body()!!, currency.id).execute()
      if (!balanceRequest.isSuccessful) {
        player.sendTranslatedMessage(
          "commons-command-economy-view-error-balance",
          "code",
          balanceRequest.code()
        )
        return
      }

      player.sendTranslatedMessage(
        "commons-command-economy-view",
        "player",
        ColoredPlayerName.from(playerName, uuidResponse.body()!!),
        "currency",
        currency,
        "balance",
        balanceRequest.body()!!.balance
      )
      return
    }

    // /economy transaction [player] [economy] [amount]
    if (args.size == 4 && args[0].equals("transaction", true) && hasAccessToCreateTransactions(player)) {
      val playerName = args[1]
      val currencyName = args[2]

      if (notNumber(args[3])) {
        player.sendTranslatedMessage(
          "commons-command-economy-transaction-error-amount",
          "amount",
          args[3]
        )
        return
      }

      val currenciesForContext = currenciesForContext(player)

      if (targetCurrencyDoesNotExists(currenciesForContext, currencyName)) {
        player.sendTranslatedMessage(
          "commons-command-economy-error-currency",
          "currency",
          currencyName
        )
        return
      }

      val currency = findCurrencyWithName(currenciesForContext, currencyName)!!

      val uuidResponse = usercacheClient.names().getUuidByName(playerName).execute()
      if (!uuidResponse.isSuccessful) {
        player.sendTranslatedMessage(
          "commons-command-economy-error-player",
          "code",
          uuidResponse.code()
        )
        return
      }

      val action: Action = if (args[3].startsWith("-")) Action.REMOVE else Action.ADD
      player.sendTranslatedMessage(
        "commons-command-economy-transaction-sending",
        "player",
        ColoredPlayerName.from(playerName, uuidResponse.body()!!),
        "currency",
        currency,
        "amount",
        args[3]
      )
      val transactionResponse = economyClient.transactions().createTransaction(
        TransactionCreateBlueprint(
          uuidResponse.body()!!,
          currency.id,
          action,
          args[3].toDouble()
        )
      ).execute()

      if (!transactionResponse.isSuccessful) {
        player.sendTranslatedMessage(
          "commons-command-economy-transaction-error-http",
          "code",
          transactionResponse.code()
        )
        return
      }

      player.sendTranslatedMessage("commons-command-economy-transaction-success")
      return
    }

    sendHelpMessage(player)
  }

  private fun notNumber(amount: String) = amount.toDoubleOrNull() == null

  private fun findCurrencyWithName(
    currenciesForContext: List<Currency>,
    currencyName: String
  ) = currenciesForContext.firstOrNull() { currency -> currency.name.equals(currencyName, true) }

  private fun targetCurrencyDoesNotExists(
    currenciesForContext: List<Currency>,
    currencyName: String
  ) = findCurrencyWithName(currenciesForContext, currencyName) == null

  private fun sendHelpMessage(player: Player) {
    var hasAccessToAnySubCommand = false

    if (hasAccessToViewEconomyData(player)) {
      player.sendTranslatedMessage("commons-command-economy-view-help")
      hasAccessToAnySubCommand = true
    }

    if (hasAccessToCreateTransactions(player)) {
      player.sendTranslatedMessage("commons-command-economy-transaction-help")
      hasAccessToAnySubCommand = true
    }

    if (!hasAccessToAnySubCommand) {
      sendAccessErrorMessage(player)
      return
    }

    player.sendTranslatedMessage(
      "commons-command-economy-currencies",
      "currencies",
      SeperatedIterable.of(currenciesForContext(player).map { currency -> currency.name })
    )
  }

  private fun hasAccessToViewEconomyData(player: Player): Boolean =
    player.hasAccess(NetworkPermissions.COMMAND_ECONOMY_VIEW)

  private fun hasAccessToCreateTransactions(player: Player): Boolean =
    player.hasAccess(NetworkPermissions.COMMAND_ECONOMY_UPDATE)

  private fun currenciesForContext(player: Player): List<Currency> {
    val currenciesResponse = economyClient.currencies().getCurrenciesForContext(context).execute()
    if (currenciesResponse.isSuccessful) {
      return currenciesResponse.body()!!
    }
    player.sendTranslatedMessage(
      "commons-command-economy-error-http",
      "code",
      currenciesResponse.code()
    )
    return emptyList()
  }
}