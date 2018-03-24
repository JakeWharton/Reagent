package reagent.operator

import reagent.runTest
import reagent.source.test.emptyActualObservable
import reagent.source.observableOf
import reagent.tester.testTask
import kotlin.test.Test

class ObservableCountTest {
  @Test fun empty() = runTest {
    emptyActualObservable<Any>()
        .count()
        .testTask {
          item(0)
        }
  }

  @Test fun nonEmpty() = runTest {
    observableOf(1, 2, 3)
        .count()
        .testTask {
          item(3)
        }
  }
}
