package reagent.rxjava2

import org.junit.Test
import reagent.source.observableOf
import reagent.source.toOne

class OneReagentToRxTest {
  @Test fun item() {
    observableOf(1)
        .toRx()
        .test()
        .assertValue(1)
        .assertComplete()
  }

  @Test fun error() {
    val exception = RuntimeException("Oops!")
    exception.toOne<Nothing>()
        .toRx()
        .test()
        .assertError(exception)
  }
}
