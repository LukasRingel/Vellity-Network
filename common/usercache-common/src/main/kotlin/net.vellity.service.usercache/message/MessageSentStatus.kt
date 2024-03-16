package net.vellity.service.usercache.message

enum class MessageSentStatus {
  UNKNOWN,
  SUCCESS,
  CANCELLED;

  companion object {
    fun byId(id: Int): MessageSentStatus {
      return when (id) {
        0 -> UNKNOWN
        1 -> SUCCESS
        2 -> CANCELLED
        else -> throw IllegalArgumentException("Unknown message sent status id: $id")
      }
    }
  }
}