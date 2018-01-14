package reagent.operator

import reagent.Many
import reagent.One

fun <I> Many<I>.all(predicate: (I) -> Boolean): One<Boolean> = ManyAll(this, predicate)

internal class ManyAll<out I>(
  private val upstream: Many<I>,
  private val predicate: (I) -> Boolean
) : One<Boolean>() {
  override suspend fun produce(): Boolean {
    var result = true
    upstream.subscribe {
      if (!predicate(it)) {
        result = false
        // TODO this needs to be return@produce false
      }
    }
    return result
  }
}
