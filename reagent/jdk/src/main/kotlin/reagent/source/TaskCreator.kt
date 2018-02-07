package reagent.source

import reagent.Task

interface TaskCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : Task.Observer<I> {
    val isDisposed: Boolean
  }
}

