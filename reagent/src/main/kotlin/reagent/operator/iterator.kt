package reagent.operator

import reagent.Observable
import reagent.Task

interface SuspendableIterator<out I> {
  suspend operator fun hasNext(): Boolean
  suspend operator fun next(): I
}

operator fun <I> Observable<I>.iterator(): SuspendableIterator<I> = ObservableIterator(this)
operator fun <I> Task<I>.iterator(): SuspendableIterator<I> = TaskIterator(this)

internal class ObservableIterator<out I>(private val observable: Observable<I>) : SuspendableIterator<I> {
  // TODO i_have_no_idea_what_im_doing.gif

  override suspend fun hasNext() = TODO()
  override suspend fun next() = TODO()
}

internal class TaskIterator<out I>(private val task: Task<I>) : SuspendableIterator<I> {
  private var done = false

  override suspend fun hasNext() = !done

  override suspend fun next(): I {
    if (done) throw IllegalStateException("Must call hasNext() before next()")
    done = true
    return task.produce()
  }
}
