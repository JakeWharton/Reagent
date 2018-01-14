package reagent.operator

import reagent.Many
import reagent.One

fun <I> Many<I>.count(): One<Int> = ManyCount(this)

internal class ManyCount<out I>(
  private val upstream: Many<I>
) : One<Int>() {
  override suspend fun produce(): Int {
    var count = 0
    upstream.subscribe {
      count++
    }
    return count
  }
}
