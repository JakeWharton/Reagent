package reagent.source

import reagent.Task
import java.util.concurrent.Callable

internal class TaskDeferredCallable<out I>(private val func: Callable<Task<I>>): Task<I>() {
  override suspend fun produce() = func.call().produce()
}
