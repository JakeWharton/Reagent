package reagent.source

import reagent.Task

internal class TaskFromSuspendingLambda<out I>(private val body: suspend () -> I): Task<I>() {
  override suspend fun produce() = body()
}
