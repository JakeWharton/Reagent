package reagent.source

import reagent.Emitter
import reagent.Observable

internal class ObservableIntProgression(private val progression: IntProgression): Observable<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    for (value in progression) {
      emit(value)
    }
  }
}
