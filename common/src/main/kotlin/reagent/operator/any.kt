package reagent.operator

import reagent.Many
import reagent.One

fun <I> Many<I>.any(predicate: (I) -> Boolean): One<Boolean> = ManyAny(this, predicate)

internal class ManyAny<out I>(
  private val upstream: Many<I>,
  private val predicate: (I) -> Boolean
) : One<Boolean>() {
  override suspend fun produce(): Boolean {
    var result = false
    upstream.subscribe {
      if (predicate(it)) {
        result = true
        // TODO this needs to be return@produce true
      }
    }
    return result
  }
}
