package reagent.source

import reagent.Task
import java.util.concurrent.Callable

internal class TaskFromCallable(private val func: Callable<*>) : Task() {
  override suspend fun run() {
    func.call()
  }
}
