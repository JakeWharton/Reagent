package reagent.source

import reagent.Task
import java.util.concurrent.Callable

internal class TaskFromCallable<out I>(private val func: Callable<I>) : Task<I>() {
  override suspend fun produce() = func.call()
}
