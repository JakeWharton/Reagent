package reagent.rxjava2

import org.junit.Test
import reagent.source.emptyObservable
import reagent.source.toTask

class TaskReagentToRxTest {
  @Test fun complete() {
    emptyObservable()
        .toRx()
        .test()
        .assertComplete()
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    exception.toTask()
        .toRx()
        .test()
        .assertError(exception)
  }
}
