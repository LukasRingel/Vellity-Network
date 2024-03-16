package net.vellity.minecraft.velocity.extensions

import com.velocitypowered.api.proxy.Player
import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.context
import net.vellity.minecraft.common.globalThreadPool
import java.util.concurrent.CompletableFuture

fun Player.hasAccess(permission: String): Boolean {
  val response = accessClient.users().hasPermission(this.uniqueId, context, permission).execute()
  if (!response.isSuccessful) {
    return false
  }
  return response.body()!!
}