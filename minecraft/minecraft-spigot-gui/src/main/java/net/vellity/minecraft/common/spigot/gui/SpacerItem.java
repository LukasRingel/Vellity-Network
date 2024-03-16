package net.vellity.minecraft.common.spigot.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpacerItem extends GuiItem{
  private static final ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
  private static final GuiItem instance = new SpacerItem();

  static {
    ItemMeta itemMeta = item.getItemMeta();
    itemMeta.displayName(Component.empty());
    item.setItemMeta(itemMeta);
  }

  public SpacerItem() {
    super(item);
  }

  public static GuiItem get() {
    return instance;
  }
}