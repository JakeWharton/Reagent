package reagent.source

import reagent.Task

internal class TaskFromLambda(private val func: () -> Unit) : Task() {
  override suspend fun run() = func()
}
