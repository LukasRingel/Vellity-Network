package net.vellity.utils.audit.counter

import net.vellity.utils.context.Context

interface CounterRepository {
  fun increaseCounter(context: Context, identifier: String)

  fun getCounter(context: Context, identifier: String): Int
}