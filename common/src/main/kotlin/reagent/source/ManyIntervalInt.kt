package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Emitter
import reagent.Many

internal class ManyIntervalInt(private val periodMillis: Int): Many<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    var count = 0
    while (true) {
      delay(periodMillis)
      emit(count++)
    }
  }
}
