package reagent.operator

import reagent.Observable
import reagent.Task

fun <I> Observable<I>.count(): Task<Int> = ObservableCount(this)

internal class ObservableCount<out I>(
  private val upstream: Observable<I>
) : Task<Int>() {
  override suspend fun produce(): Int {
    var count = 0
    upstream.subscribe {
      count++
      return@subscribe true
    }
    return count
  }
}
