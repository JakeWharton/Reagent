package reagent.operator

import reagent.Observable
import reagent.One

fun <I> Observable<I>.any(predicate: (I) -> Boolean): One<Boolean> = ObserableAny(this, predicate)

internal class ObserableAny<out I>(
  private val upstream: Observable<I>,
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
