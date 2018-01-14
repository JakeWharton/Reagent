package reagent.operator

import reagent.Emitter
import reagent.Observable
import reagent.One

fun <I> Observable<I>.take(count: Long): Observable<I> = ObservableTake(this, count)
fun <I> Observable<I>.takeOrError(count: Long): Observable<I> = ObservableTake(this, count, require = true)

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
      }
    }
    if (require && seen < count) {
      val items = if (count == 1L) "item" else "items"
      throw NoSuchElementException("Take wanted $count $items but saw $seen")
    }
  }
}
