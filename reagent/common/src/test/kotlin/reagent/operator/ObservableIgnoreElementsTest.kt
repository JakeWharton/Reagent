package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testTask
import kotlin.test.Test

class ObservableIgnoreElementsTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .ignoreElements()
        .testTask {
          complete()
        }
  }

  @Test fun items() = runTest {
    observableOf(1, 2, 3)
        .ignoreElements()
        .testTask {
          complete()
        }
  }
}
