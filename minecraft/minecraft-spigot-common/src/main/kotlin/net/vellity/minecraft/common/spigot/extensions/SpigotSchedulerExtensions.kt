package net.vellity.minecraft.common.spigot.extensions

import net.vellity.minecraft.common.spigot.VellityNetworkSpigotPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

fun sync(runnable: () -> Unit) {
  Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(VellityNetworkSpigotPlugin::class.java), runnable)
}