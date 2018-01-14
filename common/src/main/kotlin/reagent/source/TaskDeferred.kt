package reagent.source

import reagent.Task

internal class TaskDeferred(private val func: () -> Task) : Task() {
  override suspend fun run() = func().run()
}
