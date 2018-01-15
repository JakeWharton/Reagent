package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableDeferred<out I>(private val func: () -> Observable<I>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = func().subscribe(emit)
}
