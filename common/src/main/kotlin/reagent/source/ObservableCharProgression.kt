package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableCharProgression(private val progression: CharProgression): Observable<Char>() {
  override suspend fun subscribe(emit: Emitter<Char>) {
    for (value in progression) {
      emit(value)
    }
  }
}
