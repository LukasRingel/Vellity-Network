package net.vellity.service.usercache.notification

enum class Notification(
  val id: Int,
  val internalName: String,
  val permission: String,
  val defaultEnabled: Boolean
) {
  PUNISHMENT_BAN_NETWORK(
    id = 0,
    internalName = "punishment-ban-network",
    permission = "network.notification.punishment.ban.network",
    defaultEnabled = true
  ),
  PUNISHMENT_BAN_CHAT(
    id = 1,
    internalName = "punishment-ban-chat",
    permission = "network.notification.punishment.ban.chat",
    defaultEnabled = true
  ),
  PUNISHMENT_PARDON_NETWORK(
    id = 2,
    internalName = "punishment-pardon-network",
    permission = "network.notification.punishment.pardon.network",
    defaultEnabled = true
  ),
  PUNISHMENT_PARDON_CHAT(
    id = 3,
    internalName = "punishment-pardon-chat",
    permission = "network.notification.punishment.pardon.chat",
    defaultEnabled = true
  ),
  GLOBAL_BROADCAST_TIPPS(
    id = 4,
    internalName = "global-broadcast-tipps",
    permission = "network.notification.global.broadcast.tipps",
    defaultEnabled = true
  ),
  GLOBAL_BROADCAST_ADVERTISEMENT(
    id = 5,
    internalName = "global-broadcast-advertisement",
    permission = "network.notification.global.broadcast.advertisement",
    defaultEnabled = true
  ),
  GLOBAL_CHAT_STAFF(
    id = 6,
    internalName = "global-chat-staff",
    permission = "network.notification.global.chat.staff",
    defaultEnabled = true
  );

  companion object {
    fun byId(id: Int): Notification {
      for (notification in values()) {
        if (notification.id == id) {
          return notification
        }
      }
      throw IllegalArgumentException("No notification with id $id")
    }

    fun byInternalName(internalName: String): Notification? {
      for (notification in values()) {
        if (notification.internalName == internalName) {
          return notification
        }
      }
      return null
    }
  }
}
