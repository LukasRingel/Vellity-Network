package net.vellity.minecraft.common.permissions

import net.vellity.service.usercache.notification.Notification

class NetworkPermissions {
  companion object {
    const val MAINTENANCE_ACCESS = "network.maintenance.access"
    const val COMMAND_UPDATE_LOCALES = "network.common.command.updatelocales"
    const val COMMAND_PING_OTHER = "network.common.command.ping.other"
    const val COMMAND_WHERE_IS = "network.common.command.whereis"
    const val COMMAND_ECONOMY_VIEW = "network.common.command.economy.view"
    const val COMMAND_ECONOMY_UPDATE = "network.common.command.economy.update"
    const val COMMAND_CHATLOG_IGNORE = "network.common.command.chatlog.ignore"
    const val COMMAND_CHATLOG_EVERYBODY = "network.common.command.chatlog.everybody"
    const val COMMAND_PRIVATE_MESSAGE_IGNORE_RESTRICTIONS = "network.common.command.msg.ignorere.restrictions"
    const val COMMAND_PRIVATE_MESSAGE_IGNORE_CORRUPT_MESSAGES = "network.common.command.msg.ignorere.corrupt"
    const val COMMAND_PUNISHMENT_PUNISH = "network.common.command.punishment.punish"
    val CHANNEL_STAFF_GLOBAL = Notification.GLOBAL_CHAT_STAFF.permission
    val CHANNEL_STAFF_LOCAL = Notification.GLOBAL_CHAT_STAFF.permission
  }
}