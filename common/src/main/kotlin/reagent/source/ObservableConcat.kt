package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableConcat<out I>(private val observables: Iterable<Observable<I>>) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    observables.forEach { it.subscribe(emit) }
  }
}
