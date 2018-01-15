package reagent.operator

import reagent.Emitter
import reagent.Observable

fun <I> Observable<I>.distinct(): Observable<I> = ObservableDistinct(this, { it })
fun <I> Observable<I>.distinctBy(selector: (I) -> Any?): Observable<I> = ObservableDistinct(this, selector)

internal class ObservableDistinct<out I>(
  private val upstream: Observable<I>,
  private val selector: (I) -> Any?
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    val seen = mutableSetOf<Any?>()
    upstream.subscribe {
      if (seen.add(selector(it))) {
        emit(it)
      }
    }
  }
}
