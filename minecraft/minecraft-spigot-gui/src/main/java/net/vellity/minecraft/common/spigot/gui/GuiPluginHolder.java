package net.vellity.minecraft.common.spigot.gui;

import org.bukkit.plugin.java.JavaPlugin;

public class GuiPluginHolder {
  private static JavaPlugin plugin;

  public static void setPlugin(JavaPlugin plugin) {
    GuiPluginHolder.plugin = plugin;
  }

  public static JavaPlugin getPlugin() {
    return plugin;
  }
}
