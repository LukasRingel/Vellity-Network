package net.vellity.service.explorer

import net.vellity.utils.context.Context
import net.vellity.utils.httpclient.HttpClientFactory

class ExplorerClient(configuration: ExplorerClientConfiguration) {

  private val requests: ExplorerRequests

  init {
    val retrofit = HttpClientFactory.create(
      configuration.hostname,
      configuration.authKey
    )
    requests = retrofit.create(ExplorerRequests::class.java)
  }

  fun getAll(context: Context): List<Identity> {
    return requests.getAll(context).execute().body() ?: emptyList()
  }

  fun getAllOfType(type: String, context: Context): List<Identity> {
    return requests.getAllOfType(type, context).execute().body() ?: emptyList()
  }

  fun register(type: String, port: Int, address: String, context: Context): Identity {
    return requests.register(type, port, address, context).execute().body()!!
  }

  fun updateHearthBeat(type: String, id: String, context: Context, hostname: String, port: Int) {
    requests.updateHearthBeat(type, id, context, port, hostname).execute()
  }

  fun unregister(type: String, id: String, context: Context) {
    requests.unregister(type, id, context).execute()
  }
}