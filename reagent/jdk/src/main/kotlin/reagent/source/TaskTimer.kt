package reagent.source

import reagent.Task
import java.util.concurrent.TimeUnit

internal class TaskTimer(private val delay: Long, private val unit: TimeUnit): Task<Unit>() {
  override suspend fun produce() = kotlinx.coroutines.experimental.delay(delay, unit)
}
