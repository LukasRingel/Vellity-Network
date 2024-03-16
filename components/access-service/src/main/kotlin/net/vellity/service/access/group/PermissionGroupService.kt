package net.vellity.service.access.group

import net.vellity.utils.context.Context
import net.vellity.utils.redis.RedisConnection
import net.vellity.utils.redis.RedisSynchronizer
import org.springframework.stereotype.Service

@Service
class PermissionGroupService(private val repository: PermissionGroupRepository, redisConnection: RedisConnection) {
  private val synchronizer = RedisSynchronizer(redisConnection, SYNCHRONIZER_KEY) {
    updateLocalState()
  }

  private var localState: List<PermissionGroup> = repository.getPermissionGroups()

  private fun updateLocalState() {
    localState = repository.getPermissionGroups()
  }

  fun deployUpdate() =
    synchronizer.deployUpdate()

  fun getPermissionGroups(): List<PermissionGroup> =
    localState


  fun getPermissionGroupInContext(context: Context): List<PermissionGroup> =
    localState.filter { it.context == context || it.context == Context.ALL }

  fun getPermissionGroupById(id: Int): PermissionGroup? =
    localState.firstOrNull { it.id == id }

  fun repository() = repository

  companion object {
    private const val SYNCHRONIZER_KEY = "access:groups"
  }
}