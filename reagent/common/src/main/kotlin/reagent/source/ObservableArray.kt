package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableArray<out I>(private val items: Array<out I>) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    items.forEach { emit(it) }
  }
}
