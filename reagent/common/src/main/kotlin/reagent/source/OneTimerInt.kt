package reagent.source

import kotlinx.coroutines.experimental.delay
import reagent.One

internal class OneTimerInt(private val delayMillis: Int): One<Unit>() {
  override suspend fun produce() = delay(delayMillis)
}
