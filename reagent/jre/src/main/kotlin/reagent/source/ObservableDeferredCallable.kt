package reagent.source

import reagent.Emitter
import reagent.Observable
import java.util.concurrent.Callable

internal class ObservableDeferredCallable<out I>(private val func: Callable<Observable<I>>): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = func.call().subscribe(emit)
}
