package reagent.operator

import reagent.runTest
import reagent.source.test.emptyActualObservable
import reagent.source.observableOf
import reagent.tester.testOne
import kotlin.test.Test

class ObservableCountTest {
  @Test fun empty() = runTest {
    emptyActualObservable<Any>()
        .count()
        .testOne {
          item(0)
        }
  }

  @Test fun nonEmpty() = runTest {
    observableOf(1, 2, 3)
        .count()
        .testOne {
          item(3)
        }
  }
}
