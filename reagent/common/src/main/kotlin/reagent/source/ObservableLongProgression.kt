package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableLongProgression(private val progression: LongProgression): Observable<Long>() {
  override suspend fun subscribe(emit: Emitter<Long>) {
    for (value in progression) {
      emit(value)
    }
  }
}
