package reagent.operator

import reagent.Emitter
import reagent.Observable

fun <I> Observable<I>.drop(count: Long): Observable<I> = ObservableDrop(this, count)
fun <I> Observable<I>.dropOrError(count: Long): Observable<I> = ObservableDrop(this, count, require = true)

fun <I> Observable<I>.dropWhile(predicate: (I) -> Boolean): Observable<I> = ObservableDropWhile(this, predicate)

internal class ObservableDrop<out I>(
  private val upstream: Observable<I>,
  private val count: Long,
  private val require: Boolean = false
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    var seen = 0
    upstream.subscribe {
      if (++seen > count) {
        emit(it)
      }
    }
    if (require && seen < count) {
      val items = if (count == 1L) "item" else "items"
      throw NoSuchElementException("Drop wanted at least $count $items but saw $seen")
    }
  }
}

internal class ObservableDropWhile<out I>(
  private val upstream: Observable<I>,
  private val predicate: (I) -> Boolean
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    var taking = false
    upstream.subscribe upstream@ {
      if (!taking) {
        if (predicate(it)) {
          return@upstream
        }
        taking = true
      }
      emit(it)
    }
  }
}
