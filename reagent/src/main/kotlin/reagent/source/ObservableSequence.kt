package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableSequence<out I>(private val sequence: Sequence<I>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    for (item in sequence) {
      if (!emit(item)) {
        return
      }
    }
  }
}
