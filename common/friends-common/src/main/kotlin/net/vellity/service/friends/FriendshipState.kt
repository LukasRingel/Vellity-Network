package net.vellity.service.friends

enum class FriendshipState {

  NORMAL,
  FAVORITE;

  companion object {
    fun valueOf(id: Int): FriendshipState {
      return when (id) {
        0 -> NORMAL
        1 -> FAVORITE
        else -> throw IllegalArgumentException("Unknown friendship state id: $id")
      }
    }
  }
}