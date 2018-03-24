package reagent.operator

import reagent.Observable
import reagent.Task

fun <I> Observable<I>.none(predicate: (I) -> Boolean): Task<Boolean> = ObservableNone(this, predicate)

internal class ObservableNone<out I>(
  private val upstream: Observable<I>,
  private val predicate: (I) -> Boolean
) : Task<Boolean>() {
  override suspend fun produce(): Boolean {
    var result = true
    upstream.subscribe {
      if (result && predicate(it)) {
        result = false
        return@subscribe false
      }
      return@subscribe true
    }
    return result
  }
}
