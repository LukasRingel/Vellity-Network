package net.vellity.service.config

import net.vellity.utils.context.Context
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ConfigRequests {
  @GET("/configurations")
  fun getConfiguration(
    @Query("context") context: Context,
    @Query("path") path: String
  ): Call<ResponseBody>
}