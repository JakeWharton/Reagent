package reagent.operator

import reagent.Emitter
import reagent.Observable

fun <I> Observable<I>.distinctUntilChanged(): Observable<I> = ObservableDistinctUntilChanged(this, { it })
fun <I> Observable<I>.distinctUntilChangedBy(selector: (I) -> Any?): Observable<I> = ObservableDistinctUntilChanged(this, selector)

internal class ObservableDistinctUntilChanged<out I>(
  private val upstream: Observable<I>,
  private val selector: (I) -> Any?
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    var first = true
    var previous: Any? = null
    upstream.subscribe {
      val selected = selector(it)
      if (first) {
        previous = selected
        first = false
        emit(it)
      } else if (selected != previous) {
        previous = selected
        emit(it)
      } else {
        true
      }
    }
  }
}
