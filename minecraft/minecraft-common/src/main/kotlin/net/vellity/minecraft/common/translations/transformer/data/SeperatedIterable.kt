package net.vellity.minecraft.common.translations.transformer.data

class SeperatedIterable(
  val iterable: Iterable<Any>,
  val seperator: String = ", ",
  private val dataColor: (Any) -> String = { "info" }
) {

  fun decorate(any: String): String {
    val color = dataColor(any)
    return "<$color>$any</$color>"
  }

  companion object {
    fun of(iterable: Iterable<Any>): SeperatedIterable {
      return SeperatedIterable(iterable)
    }

    fun of(vararg iterable: Any): SeperatedIterable {
      return SeperatedIterable(iterable.toList())
    }

    fun of(iterable: Iterable<Any>, color: (Any) -> String): SeperatedIterable {
      return SeperatedIterable(iterable, dataColor = color)
    }

    fun of(vararg iterable: Any, color: (Any) -> String): SeperatedIterable {
      return SeperatedIterable(iterable.toList(), dataColor = color)
    }
  }
}