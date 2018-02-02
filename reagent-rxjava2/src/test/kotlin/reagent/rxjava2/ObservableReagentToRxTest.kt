package reagent.rxjava2

import org.junit.Test
import reagent.Observable
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.source.toOne

class ObservableReagentToRxTest {
  @Test fun empty() {
    emptyObservable()
        .toRx()
        .test()
        .assertComplete()
  }

  @Test fun singleItem() {
    (observableOf(1) as Observable<Int>)
        .toRx()
        .test()
        .assertValue(1)
        .assertComplete()
  }

  @Test fun multipleItems() {
    observableOf(1, 2, 3)
        .toRx()
        .test()
        .assertValues(1, 2, 3)
        .assertComplete()
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    exception.toOne()
        .toRx()
        .test()
        .assertError(exception)
  }
}
