package reagent.rxjava2

import org.junit.Test
import reagent.Maybe
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.source.toTask

class MaybeReagentToRxTest {
  @Test fun nothing() {
    (emptyObservable() as Maybe<Nothing>)
        .toRx()
        .test()
        .assertComplete()
  }

  @Test fun item() {
    (observableOf(1) as Maybe<Int>)
        .toRx()
        .test()
        .assertValue(1)
        .assertComplete()
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    (exception.toTask() as Maybe<Nothing>)
        .toRx()
        .test()
        .assertError(exception)
  }
}
