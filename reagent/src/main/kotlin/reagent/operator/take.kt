package reagent.operator

import reagent.Emitter
import reagent.Observable

fun <I> Observable<I>.take(count: Long): Observable<I> = ObservableTake(this, count)
fun <I> Observable<I>.takeOrError(count: Long): Observable<I> = ObservableTake(this, count, require = true)

fun <I> Observable<I>.takeWhile(predicate: (I) -> Boolean): Observable<I> = ObservableTakeWhile(this, predicate)

internal class ObservableTake<out I>(
  private val upstream: Observable<I>,
  private val count: Long,
  private val require: Boolean = false
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    var seen = 0
    upstream.subscribe {
      if (seen++ < count) {
        emit(it)
      } else {
        false
      }
    }
    if (require && seen < count) {
      val items = if (count == 1L) "item" else "items"
      throw NoSuchElementException("Take wanted $count $items but saw $seen")
    }
  }
}

internal class ObservableTakeWhile<out I>(
  private val upstream: Observable<I>,
  private val predicate: (I) -> Boolean
) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    var taking = true
    upstream.subscribe upstream@ {
      if (taking) {
        if (predicate(it)) {
          return@upstream emit(it)
        } else {
          taking = false
        }
      }
      return@upstream false
    }
  }
}
