package reagent.source

import reagent.Task

interface TaskCreator {
  fun subscribe(downstream: Downstream)

  interface Downstream : Task.Observer {
    val isDisposed: Boolean
  }
}
