package reagent.source

import reagent.Task

internal class TaskError(private val t: Throwable) : Task<Nothing>() {
  override suspend fun produce() = throw t
}
