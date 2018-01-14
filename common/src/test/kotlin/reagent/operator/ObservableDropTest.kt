package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test

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
          error { it is NoSuchElementException && it.message == "Drop wanted at least 1 item but saw 0" }
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
