package net.vellity.minecraft.velocity.motd

import net.kyori.adventure.text.Component
import net.vellity.minecraft.common.configClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.translations.format.MiniMessageFormat
import net.vellity.utils.configuration.fromJson
import java.util.concurrent.Executors

object MotDService {

  var motdDisplay: Component = Component.text("")
  var slots: Int = 0

  fun startUpdateTask() {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      updateRepository()
    }, 0, 30, java.util.concurrent.TimeUnit.SECONDS)
  }

  fun updateRepository() {
    try {
      val response = configClient.configs().getConfiguration(context, "network-motd").execute()
      if (!response.isSuccessful) {
        throw RuntimeException("Failed to update motd configuration")
      }
      val configuration = fromJson(response.body()!!.string(), MotDConfiguration::class.java)
      motdDisplay = MiniMessageFormat.format(configuration.line1)
        .append(Component.newline())
        .append(MiniMessageFormat.format(configuration.line2))
      slots = configuration.slots
    } catch (e: Exception) {
      e.printStackTrace()
    }

  }
}