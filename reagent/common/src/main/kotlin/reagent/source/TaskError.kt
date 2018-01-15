package reagent.source

import reagent.Task

internal class TaskError(private val t: Throwable) : Task() {
  override suspend fun run() = throw t
}
