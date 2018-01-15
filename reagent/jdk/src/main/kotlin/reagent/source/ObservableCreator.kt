package reagent.source

import reagent.Observable

interface ObservableCreator<out I> {
  fun subscribe(downstream: Downstream<I>)

  interface Downstream<in I> : Observable.Observer<I> {
    val isDisposed: Boolean
  }
}

