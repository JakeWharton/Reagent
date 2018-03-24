package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableIterable<out I>(private val iterable: Iterable<I>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    for (item in iterable) {
      if (!emit(item)) {
        return
      }
    }
  }
}
