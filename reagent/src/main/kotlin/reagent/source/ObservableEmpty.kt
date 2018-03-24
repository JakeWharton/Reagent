package reagent.source

import reagent.Emitter
import reagent.Observable

internal object ObservableEmpty : Observable<Nothing>() {
  override suspend fun subscribe(emit: Emitter<Nothing>) = Unit
}
