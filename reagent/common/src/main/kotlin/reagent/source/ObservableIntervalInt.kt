package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Emitter
import reagent.Observable

internal class ObservableIntervalInt(private val periodMillis: Int): Observable<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    var count = 0
    while (true) {
      delay(periodMillis)
      emit(count++)
    }
  }
}
