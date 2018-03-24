package reagent.operator

import reagent.Emitter
import reagent.Observable

fun Observable<*>.ignoreElements(): Observable<Nothing> = ObservableIgnoreElements(this)

internal class ObservableIgnoreElements(
  private val upstream: Observable<*>
): Observable<Nothing>() {
  override suspend fun subscribe(emit: Emitter<Nothing>) {
    upstream.subscribe { true }
  }
}
