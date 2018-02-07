package reagent.rxjava2

import org.junit.Test
import reagent.Observable
import reagent.source.emptyObservable
import reagent.source.observable
import reagent.source.observableOf
import reagent.source.toTask
import kotlin.test.assertEquals

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
    exception.toTask()
        .toRx()
        .test()
        .assertError(exception)
  }

  @Test fun upstreamNotified() {
    val results = mutableListOf<Boolean>()
    observable<Int> {
      for (i in 1..2) {
        results.add(it(i))
      }
    }.toRx().take(2).test().assertValues(1, 2).assertComplete()
    assertEquals(listOf(true, false), results)
  }
}
