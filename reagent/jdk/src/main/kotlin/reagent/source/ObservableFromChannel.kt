package reagent.source

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import reagent.Emitter
import reagent.Observable

internal class ObservableFromChannel<out I>(private val channel: ReceiveChannel<I>) : Observable<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    for (item in channel) {
      if (!emit(item)) {
        return
      }
    }
  }
}
