package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableSequence<out I>(private val iterable: Sequence<I>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    iterable.forEach { emit(it) }
  }
}
