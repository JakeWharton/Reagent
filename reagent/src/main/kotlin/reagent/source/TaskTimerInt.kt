package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.Task

internal class TaskTimerInt(private val delayMillis: Int): Task<Unit>() {
  override suspend fun produce() = delay(delayMillis)
}
