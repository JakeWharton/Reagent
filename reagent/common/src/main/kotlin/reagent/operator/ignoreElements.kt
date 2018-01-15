package reagent.operator

import reagent.Observable
import reagent.Task

fun Observable<*>.ignoreElements(): Task = ObservableIgnoreElements(this)

internal class ObservableIgnoreElements(
  private val upstream: Observable<*>
): Task() {
  override suspend fun run() {
    upstream.subscribe { }
  }
}
