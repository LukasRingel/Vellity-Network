package net.vellity.utils.configuration

import com.google.gson.Gson
import java.io.Reader

val gson: Gson = Gson()

fun toJson(any: Any): String = gson.toJson(any)

fun <T> fromJson(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)

fun <T> fromJson(json: Reader, clazz: Class<T>): T = gson.fromJson(json, clazz)