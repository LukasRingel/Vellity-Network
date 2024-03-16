package net.vellity.service.worker.worker.dailyreport

import club.minnced.discord.webhook.WebhookClient
import club.minnced.discord.webhook.send.WebhookEmbed
import club.minnced.discord.webhook.send.WebhookEmbedBuilder
import net.vellity.utils.configuration.Environment
import net.vellity.utils.context.Context
import net.vellity.utils.mysql.MySqlConnection
import net.vellity.utils.mysql.extensions.nowUtc
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DailyReportBroadcaster(private val mySqlConnection: MySqlConnection) {

  @Scheduled(cron = "0 * * * * *")
  fun broadcast() {

    val url = Environment.getOrDefault("WORKER_DAILY_REPORT_URL", "")

    if (url.isEmpty()) {
      LoggerFactory.getLogger(DailyReportBroadcaster::class.java)
        .error("Skipping daily report broadcast due to missing url.")
      return
    }

    val globalDailyLoginsAmount = mySqlConnection.executeQuery(query = SelectYesterdayGlobalDailyLoginsAmount()).first()
    val globalDailyMessagesAmount = mySqlConnection.executeQuery(SelectYesterdayGlobalMessagesAmount()).first()
    val globalDailyTransactionsAmount = mySqlConnection.executeQuery(SelectYesterdayGlobalTransactionsAmount()).first()

    val client = WebhookClient.withUrl(url)
    val webhookEmbed = WebhookEmbedBuilder()
      .setTitle(title())
      .setThumbnailUrl(Context.ALL.imageUrl)
      .setDescription("Daily Global Report for " + formatDateToOnlyDay())
      .addField(spaceField())
      .addField(field("Global logins", globalDailyLoginsAmount))
      .addField(field("Global messages", globalDailyMessagesAmount))
      .addField(field("Global transactions", globalDailyTransactionsAmount))
      .build()
    client.send(webhookEmbed).whenComplete { message, _ ->
      sendContextReportToThread(Context.FISHINGTOWN, message.id, client)
      sendContextReportToThread(Context.MCMINIGAME, message.id, client)
      sendContextReportToThread(Context.NERUX, message.id, client)
      LoggerFactory.getLogger(DailyReportBroadcaster::class.java).info("Sent daily report to discord")
    }
  }

  private fun sendContextReportToThread(context: Context, messageId: Long, client: WebhookClient) {
    client.onThread(messageId).send(WebhookEmbedBuilder()
      .setTitle(title())
      .setThumbnailUrl(context.imageUrl)
      .setDescription("Daily ${context.name} Report for " + formatDateToOnlyDay())
      .addField(spaceField())
      .addField(field("Logins", mySqlConnection.executeQuery(SelectYesterdayGlobalDailyLoginsAmount(context)).first()))
      .addField(field("Messages", mySqlConnection.executeQuery(SelectYesterdayGlobalMessagesAmount(context)).first()))
      .build())
      .get()
  }

  private fun formatDateToOnlyDay(): String {
    return nowUtc().toString().substring(0, 10)
  }

  private fun title() = WebhookEmbed.EmbedTitle(
    "Daily Network Report (${Environment.getOrDefault("ENVIRONMENT", "productive")})",
    null
  )

  private fun spaceField() = WebhookEmbed.EmbedField(
    false,
    " ",
    " "
  )

  private fun field(name: String, value: String) = WebhookEmbed.EmbedField(
    true,
    name,
    value
  )

  private fun field(name: String, value: Int) = field(name, value.toString())
}