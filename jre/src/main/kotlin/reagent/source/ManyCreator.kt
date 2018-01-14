package reagent.source

import reagent.Many

interface ManyCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : Many.Observer<I> {
    val isDisposed: Boolean
  }
}

