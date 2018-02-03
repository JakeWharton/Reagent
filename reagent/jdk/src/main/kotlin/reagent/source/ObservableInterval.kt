package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Emitter
import reagent.Observable
import java.util.concurrent.TimeUnit

internal class ObservableInterval(private val period: Long, private val unit: TimeUnit): Observable<Int>() {
  override suspend fun subscribe(emit: Emitter<Int>) {
    var count = 0
    while (true) {
      delay(period, unit)
      if (!emit(count++)) {
        return
      }
    }
  }
}
