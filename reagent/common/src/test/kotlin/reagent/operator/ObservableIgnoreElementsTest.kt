package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test

class ObservableIgnoreElementsTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .ignoreElements()
        .testObservable {
          complete()
        }
  }

  @Test fun items() = runTest {
    observableOf(1, 2, 3)
        .ignoreElements()
        .testObservable {
          complete()
        }
  }
}
