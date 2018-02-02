package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableFromRunnable(private val func: Runnable) : Observable<Nothing>() {
  override suspend fun subscribe(emit: Emitter<Nothing>) = func.run()
}
