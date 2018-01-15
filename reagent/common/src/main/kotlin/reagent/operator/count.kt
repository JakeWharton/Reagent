package reagent.operator

import reagent.Observable
import reagent.One

fun <I> Observable<I>.count(): One<Int> = ObservableCount(this)

internal class ObservableCount<out I>(
  private val upstream: Observable<I>
) : One<Int>() {
  override suspend fun produce(): Int {
    var count = 0
    upstream.subscribe {
      count++
    }
    return count
  }
}
