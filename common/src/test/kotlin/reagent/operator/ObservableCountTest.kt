package reagent.operator

import reagent.runTest
import reagent.source.test.emptyMany
import reagent.source.observableOf
import reagent.tester.testOne
import kotlin.test.Test

class ObservableCountTest {
  @Test fun empty() = runTest {
    emptyMany<Any>()
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
