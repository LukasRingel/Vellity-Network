package net.vellity.minecraft.common

import net.vellity.service.explorer.Identity
import net.vellity.utils.configuration.Environment
import net.vellity.utils.context.Context
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val globalThreadPool: ExecutorService = Executors.newCachedThreadPool()

val context: Context = Context.current()

var identity: Identity = Identity("", Context.UNKNOWN, "", "", 0)

val serverGroupName: String = Environment.getOrDefault("SERVER_GROUP", "unknown")