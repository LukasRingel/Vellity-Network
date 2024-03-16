package net.vellity.minecraft.common.spigot.item.textures

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.text.ParseException

class MineSkinStrategy(private val mineSkinId: String) : TextureStrategy {
  override fun textureValue(): String {
    if (Thread.currentThread().name.equals("Server thread", true)) {
      throw IllegalStateException("Cannot download texture on main thread")
    }
    return download(mineSkinId).value
  }

  private fun download(url: String?): MineSkinResult {
    try {
      val con: HttpURLConnection = buildHttpURLConnection()
      val dataOutputStream = DataOutputStream(con.outputStream)
      dataOutputStream.writeBytes("url=" + URLEncoder.encode(url, "UTF-8"))
      dataOutputStream.close()
      val reader = BufferedReader(InputStreamReader(con.inputStream))
      val output =
        JSONParser().parse(reader) as JSONObject
      val data = output["data"] as JSONObject
      val uuid = data["uuid"] as String
      val texture = data["texture"] as JSONObject
      val textureEncoded = texture["value"] as String
      val signature = texture["signature"] as String
      return MineSkinResult(uuid, textureEncoded, signature)
    } catch (exception: IOException) {
      return FALLBACK
    } catch (exception: ParseException) {
      return FALLBACK
    }
  }

  @Throws(IOException::class)
  private fun buildHttpURLConnection(): HttpURLConnection {
    val target = URL(URL)
    val con: HttpURLConnection = target.openConnection() as HttpURLConnection
    con.setRequestMethod("POST")
    con.setDoOutput(true)
    con.setConnectTimeout(1000)
    con.setReadTimeout(30000)
    return con
  }

  private data class MineSkinResult(
    val uuid: String,
    val signature: String,
    val value: String
  )

  companion object {
    private const val URL = "https://api.mineskin.org/generate/url"
    private val FALLBACK = MineSkinResult(
      "742eb1bc3b41445c8e3bef51535bf435",
      "nnMt+n50dRoN5svTwxTsyzl/alouayqbAeSXFoJ2XagYevGsh+iCmIDIysYgJ5OXSl5QpDBeF/KAinf54fgk0Eat2KAStMQwcP" +
        "MkHnaQTmey6GMGOIc2ZtGLwlybaEMtX/4wbANFswqECA7+CjxWvgOTm9//wX2fyblV961oAkaCCpw+OrojlQ6/KfahG2XgyEhdmJhnSw44" +
        "XLD/LCXyCd97HSpTfmLgZYqXZtBnafMSwSbFi1bcbdwqRBcIm6VnfW9lgD0KdgCN/G+26kMtS55J50BprO8vZpv0lYxmJ9UzVnkQKDCYKz" +
        "WAiEBmTMon+Sxet4K4uOs8OxlkCwEMHWCzR5MjCWESKvDWlZHkBnejY8RsIYbDn1CxWQ6zjlgJ1DlPZtYHlodlTVoKMuaeit21f2bvEdN6" +
        "GvemzDtPwZI25hLt6/X/3axY3tQUeKoqu5rsa0L2PsjjNswEUJcO4AQynQlwAw7BVv5GBgITrN/zW3S6UmA+btft7zTB6/y8JcEJhx0m96" +
        "AR5bwo67liBw+7EvyxRvQFKocQIL83Pvo7fsB+tmIpjwFSaCQEiCh47AjAsvnjqbzwbfZ7ttSFJUk4piVgRXyyEORBAIjNjEH+2J0TILy9" +
        "7k12/HJKkG9C/KXdOYv88j6paQKSWE6OuCxolcTwJWpcTtzh7xD2Lxs=",
      "ewogICJ0aW1lc3RhbXAiIDogMTYyMjc1MDc3ODY4NywKICAicHJvZmlsZUlkIiA6ICJmOTJiZDcxNGE0NmE0MmQ0OTQ1ZjdiMGNmN" +
        "zExMDllNiIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGlrb2xpbmsiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJ" +
        "lcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWNmY" +
        "WU4NjM4ZmNkYzRlNDk2ODdmMjFmZGEwN2EzOThmYjc0Mjk4NjBhZDU5NzAwNjAxYTA0OGI5N2ExODFmNCIKICAgIH0KICB9Cn0="
    )
  }
}