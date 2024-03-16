package net.vellity.utils.httpclient

import com.google.gson.GsonBuilder
import net.vellity.utils.configuration.Environment
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

object HttpClientFactory {
  private const val API_KEY_HEADER = "API-KEY"
  private const val INJECT_URL = "https://localhost"

  private val logRequests = Environment.getAsBooleanOrDefault("CLIENT_LOGGING", true)
  private val logRequestsFilter = mutableListOf(
    "explorer/registry",
    "babbel/translations"
  )

  fun create(hostname: String, authKey: String): Retrofit {
    return Retrofit.Builder().baseUrl(INJECT_URL)
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .addConverterFactory(
        GsonConverterFactory.create(
          GsonBuilder().registerTypeAdapter(
            Instant::class.java,
            InstantConverter()
          ).create()
        )
      )
      .client(OkHttpClient().newBuilder().addInterceptor(Interceptor {
        val request = it.request().newBuilder()
          .url(formatUrl(it.request().url(), hostname))
          .addHeader(API_KEY_HEADER, authKey)
          .build()

        if (logRequests) {
          logRequestToConsoleIfNotFiltered(request)
        }

        return@Interceptor it.proceed(request)
      }).build()).build()
  }

  private fun logRequestToConsoleIfNotFiltered(request: Request) {
    for (urlPath in logRequestsFilter) {
      if (request.url().toString().contains(urlPath)) {
        return
      }
    }
    println("${Thread.currentThread().name}: ${request.method()} ${request.url()}")
  }

  private fun formatUrl(url: HttpUrl, hostname: String): String {
    return url.toString().replace(
      INJECT_URL,
      if (hostname.endsWith("/")) {
        hostname.removeRange(hostname.length - 1, hostname.length)
      } else {
        hostname
      }
    )
  }
}