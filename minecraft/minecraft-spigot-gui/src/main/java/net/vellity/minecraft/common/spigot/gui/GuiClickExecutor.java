package net.vellity.minecraft.common.spigot.gui;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GuiClickExecutor {
  private static Executor executor = Executors.newCachedThreadPool();

  public static void setExecutor(Executor executor) {
    GuiClickExecutor.executor = executor;
  }

  public static void execute(Runnable runnable) {
    executor.execute(runnable);
  }
}
