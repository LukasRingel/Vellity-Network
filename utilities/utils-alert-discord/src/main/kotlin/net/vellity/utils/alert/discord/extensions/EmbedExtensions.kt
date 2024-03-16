package net.vellity.utils.alert.discord.extensions

import club.minnced.discord.webhook.send.WebhookEmbed

fun field(name: String, value: String) = WebhookEmbed.EmbedField(
  true,
  name,
  value
)

fun field(name: String, value: Int) = field(name, value.toString())