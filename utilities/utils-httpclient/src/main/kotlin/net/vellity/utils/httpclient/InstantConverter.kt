package net.vellity.utils.httpclient

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.Instant

class InstantConverter : TypeAdapter<Instant>() {
  override fun write(out: JsonWriter?, value: Instant?) {
    out?.value(value?.toString())
  }

  override fun read(`in`: JsonReader?): Instant {
    return Instant.parse(`in`?.nextString())
  }
}