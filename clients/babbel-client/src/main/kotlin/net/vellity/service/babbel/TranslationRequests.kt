package net.vellity.service.babbel

import retrofit2.Call
import net.vellity.utils.context.Context
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationRequests {
  @GET("/translations")
  fun bundle(@Query("context") context: Context): Call<Bundle>
}