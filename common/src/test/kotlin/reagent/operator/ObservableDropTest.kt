package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ObservableDropTest {
  @Test fun emptyDrop() = runTest {
    emptyObservable()
        .drop(1)
        .testObservable {
          complete()
        }
  }

  @Test fun emptyDropOrError() = runTest {
    emptyObservable()
        .dropOrError(1)
        .testObservable {
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("Drop wanted at least 1 item but saw 0", it.message)
          }
        }
  }

  @Test fun oneDropOne() = runTest {
    observableOf(1)
        .drop(1)
        .testObservable {
          complete()
        }
  }

  @Test fun oneDropOneOrError() = runTest {
    observableOf(1)
        .dropOrError(1)
        .testObservable {
          complete()
        }
  }

  @Test fun twoDropOne() = runTest {
    observableOf(1, 2)
        .drop(1)
        .testObservable {
          item(2)
          complete()
        }
  }

  @Test fun twoDropOneOrError() = runTest {
    observableOf(1, 2)
        .dropOrError(1)
        .testObservable {
          item(2)
          complete()
        }
  }
}
