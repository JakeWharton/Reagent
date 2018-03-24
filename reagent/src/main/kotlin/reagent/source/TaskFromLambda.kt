package reagent.source

import reagent.Task

internal class TaskFromLambda<out I>(private val func: () -> I) : Task<I>() {
  override suspend fun produce() = func()
}
