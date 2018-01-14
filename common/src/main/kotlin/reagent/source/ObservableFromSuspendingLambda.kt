package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableFromSuspendingLambda<out I>(
  private val body: suspend (emit: Emitter<I>) -> Unit
): Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) = body(emit)
}
