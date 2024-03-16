package net.vellity.minecraft.common.spigot.extensions

import net.vellity.minecraft.common.accessClient
import net.vellity.minecraft.common.context
import net.vellity.service.access.MetaData
import net.vellity.service.access.user.AssignedGroup
import org.bukkit.entity.Player

object PlayerAccessExtensions {

  fun Player.hasAccess(permission: String): Boolean {
    val response = accessClient.users().hasPermission(this.uniqueId, context, permission).execute()
    if (!response.isSuccessful) {
      return false
    }
    return response.body()!!
  }

  fun Player.highestGroup(): AssignedGroup? {
    val response = accessClient.users().get(this.uniqueId, context).execute()
    if (!response.isSuccessful) {
      return null
    }
    return response.body()!!.groups.maxBy {
      it.group
        .getMetaDataValue(MetaData.TAB_LIST_PRIORITY.name, "1")
        .toInt()
    }
  }

}