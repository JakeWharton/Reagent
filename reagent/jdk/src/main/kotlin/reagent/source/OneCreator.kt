package reagent.source

import reagent.One

interface OneCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : One.Observer<I> {
    val isDisposed: Boolean
  }
}

