package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Emitter
import reagent.Many
import java.util.concurrent.TimeUnit

internal class ManyInterval(private val period: Long, private val unit: TimeUnit): Many<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    var count = 0
    while (true) {
      delay(period, unit)
      emit(count++)
    }
  }
}
