package reagent.source

import reagent.Task

internal class TaskDeferred<out I>(private val func: () -> Task<I>) : Task<I>() {
  override suspend fun produce() = func().produce()
}
