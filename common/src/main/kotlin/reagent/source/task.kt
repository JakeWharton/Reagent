package reagent.source

import reagent.Task

fun emptyTask(): Task = TaskComplete
fun taskRunning(func: () -> Unit): Task = TaskFromLambda(func)

fun deferTask(func: () -> Task): Task = TaskDeferred(func)

fun Throwable.toTask(): Task = TaskError(this)

internal object TaskComplete : Task() {
  override suspend fun run() = Unit
}

internal class TaskError(private val t: Throwable) : Task() {
  override suspend fun run() = throw t
}

internal class TaskFromLambda(private val func: () -> Unit) : Task() {
  override suspend fun run() = func()
}

internal class TaskDeferred(private val func: () -> Task): Task() {
  override suspend fun run() = func().run()
}
