package reagent.operator

import reagent.Many
import reagent.Maybe
import reagent.One
import reagent.Task

interface SuspendableIterator<out I> {
  suspend operator fun hasNext(): Boolean
  suspend operator fun next(): I
}

operator fun <I> Many<I>.iterator(): SuspendableIterator<I> = ManyIterator(this)
operator fun <I> Maybe<I>.iterator(): SuspendableIterator<I> = MaybeIterator(this)
operator fun <I> One<I>.iterator(): SuspendableIterator<I> = OneIterator(this)
operator fun Task.iterator(): SuspendableIterator<Nothing> = TaskIterator(this)

internal class ManyIterator<out I>(private val many: Many<I>) : SuspendableIterator<I> {
  // TODO i_have_no_idea_what_im_doing.gif

  override suspend fun hasNext() = TODO()
  override suspend fun next() = TODO()
}

internal class MaybeIterator<out I>(private val maybe: Maybe<I>) : SuspendableIterator<I> {
  private var done = false
  private var value: I? = null

  override suspend fun hasNext(): Boolean {
    if (done) return false
    done = true
    value = maybe.produce()
    return value != null
  }

  override suspend fun next(): I {
    val value = if (done) {
      value
    } else {
      done = true
      maybe.produce()
    }
    value?.let {
      this.value = null
      return it
    }
    throw IllegalStateException("Must call hasNext() before next()")
  }
}

internal class OneIterator<out I>(private val one: One<I>) : SuspendableIterator<I> {
  private var done = false

  override suspend fun hasNext() = !done

  override suspend fun next(): I {
    if (done) throw IllegalStateException("Must call hasNext() before next()")
    done = true
    return one.produce()
  }
}

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
