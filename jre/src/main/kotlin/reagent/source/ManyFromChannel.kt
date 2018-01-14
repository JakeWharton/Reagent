package reagent.source

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.consumeEach
import reagent.Emitter
import reagent.Many

internal class ManyFromChannel<out I>(private val channel: ReceiveChannel<I>) : Many<I>() {
  override suspend fun subscribe(emit: Emitter<I>) {
    channel.consumeEach {
      emit(it)
    }
  }
}
