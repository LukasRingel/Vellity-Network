package net.vellity.utils.context

import net.vellity.utils.configuration.Environment

enum class Context(val imageUrl: String) {

  UNKNOWN("https://img.lukasringel.de/53663968-3a11-4322-a4e7-4eebef01cfc2.png"),
  ALL("https://img.lukasringel.de/d3290322-7d62-4325-bddc-586cfdd54837.png"),
  POSTMAN("https://img.lukasringel.de/2e0b1116-d711-464a-80d2-2923ae320bce.png"),
  MAPEDIT("https://img.lukasringel.de/d3290322-7d62-4325-bddc-586cfdd54837.png"),
  FISHINGTOWN("https://img.lukasringel.de/e7618593-2921-4079-954a-faa54cece64c.png"),
  MCMINIGAME("https://img.lukasringel.de/c73361b5-a54c-417d-a684-11a5a830c193.png"),
  NERUX("https://img.lukasringel.de/722643f0-7b47-449b-9c3b-f3ad81822003.png");

  companion object {
    private var currentContext: Context = Context.valueOf(
      Environment.getOrDefault("CONTEXT", "UNKNOWN"))

    fun current(): Context = currentContext

    fun currentId(): Int = current().id()

    fun isCurrent(context: Context): Boolean {
      return currentContext == ALL || currentContext == context
    }

    fun isCurrent(id: Int): Boolean = isCurrent(values()[id])

    fun valueOf(id: Int): Context = values()[id]

    fun Context.id(): Int {
      return ordinal
    }
  }
}