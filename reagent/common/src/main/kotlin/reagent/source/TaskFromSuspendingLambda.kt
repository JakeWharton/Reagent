package reagent.source

import reagent.Task

internal class TaskFromSuspendingLambda(private val body: suspend () -> Unit) : Task() {
  override suspend fun run() = body()
}
