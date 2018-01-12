package reagent

operator fun Task.iterator(): SuspendableIterator<Nothing> = TaskIterator(this)

internal class TaskIterator(private val task: Task) : SuspendableIterator<Nothing> {
  private var done = false

  override suspend fun hasNext(): Boolean {
    if (!done) {
      task.run()
      done = true
    }
    return false
  }

  override suspend fun next() = throw IllegalStateException("Must call hasNext() before next()")
}
