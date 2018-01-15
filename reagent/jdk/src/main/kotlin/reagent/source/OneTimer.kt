package reagent.source

import reagent.One
import java.util.concurrent.TimeUnit

internal class OneTimer(private val delay: Long, private val unit: TimeUnit): One<Unit>() {
  override suspend fun produce() = kotlinx.coroutines.experimental.delay(delay, unit)
}
