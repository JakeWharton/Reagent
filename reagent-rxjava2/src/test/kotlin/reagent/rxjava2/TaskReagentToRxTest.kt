package reagent.rxjava2

import org.junit.Test
import reagent.source.observableOf
import reagent.source.toTask

class TaskReagentToRxTest {
  @Test fun item() {
    observableOf(1)
        .toRx()
        .test()
        .assertValue(1)
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
