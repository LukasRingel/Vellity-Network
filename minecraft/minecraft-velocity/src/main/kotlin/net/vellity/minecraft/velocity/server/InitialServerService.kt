package net.vellity.minecraft.velocity.server

import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.configClient
import net.vellity.minecraft.common.context
import net.vellity.utils.configuration.fromJson
import java.util.*
import java.util.concurrent.Executors

object InitialServerService {

  var configuration: InitialServerConfiguration = InitialServerConfiguration(
    "lobby",
    mapOf()
  )

  fun startUpdateTask() {
    Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
      updateRepository()
    }, 0, 30, java.util.concurrent.TimeUnit.SECONDS)
  }

  fun getInitialServerGroupForPlayer(uuid: UUID): String {
    val userResponse = accessClient.users().get(uuid).execute()
    if (!userResponse.isSuccessful) {
      throw RuntimeException("Failed to get user")
    }

    val permissionUser = userResponse.body()!!
    permissionUser.groups.firstOrNull { group -> configuration.accessGroupToServerGroup.containsKey(group.group.id) }
      ?.let { group ->
        return configuration.accessGroupToServerGroup[group.group.id]!!
      }

    return configuration.fallbackServerGroup
  }

  fun updateRepository() {
    try {
      val response = configClient.configs().getConfiguration(context, "network-initial-servers").execute()
      if (!response.isSuccessful) {
        throw RuntimeException("Failed to update initial servers configuration")
      }
      configuration = fromJson(response.body()!!.string(), InitialServerConfiguration::class.java)
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}