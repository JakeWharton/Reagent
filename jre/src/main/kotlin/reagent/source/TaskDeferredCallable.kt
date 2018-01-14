package reagent.source

import reagent.Task
import java.util.concurrent.Callable

internal class TaskDeferredCallable(private val func: Callable<Task>): Task() {
  override suspend fun run() = func.call().run()
}
