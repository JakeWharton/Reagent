package reagent.operator

import reagent.Observable
import reagent.Task

fun <I> Observable<I>.any(predicate: (I) -> Boolean): Task<Boolean> = ObserableAny(this, predicate)

internal class ObserableAny<out I>(
  private val upstream: Observable<I>,
  private val predicate: (I) -> Boolean
) : Task<Boolean>() {
  override suspend fun produce(): Boolean {
    var result = false
    upstream.subscribe {
      if (predicate(it)) {
        result = true
        return@subscribe false
      }
      return@subscribe true
    }
    return result
  }
}
