package reagent.source

import reagent.Maybe

interface MaybeCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : Maybe.Observer<I> {
    val isDisposed: Boolean
  }
}

