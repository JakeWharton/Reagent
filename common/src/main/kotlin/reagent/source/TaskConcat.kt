package reagent.source

import reagent.Task

internal class TaskConcat(private val tasks: Iterable<Task>): Task() {
  override suspend fun run() {
    tasks.forEach { it.run() }
  }
}
