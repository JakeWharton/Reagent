package reagent.source

import reagent.Task

internal class TaskFromRunnable(private val func: Runnable) : Task() {
  override suspend fun run() = func.run()
}
