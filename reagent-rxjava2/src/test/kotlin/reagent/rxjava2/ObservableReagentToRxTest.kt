package reagent.rxjava2

import org.junit.Test
import reagent.Observable
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.source.toTask

class ObservableReagentToRxTest {
  @Test fun empty() {
    (emptyObservable() as Observable<Nothing>)
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
    (exception.toTask() as Observable<Nothing>)
        .toRx()
        .test()
        .assertError(exception)
  }
}
