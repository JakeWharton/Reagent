package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test

class ObservableTaskTest {
  @Test fun emptyTake() = runTest {
    emptyObservable()
        .take(1)
        .testObservable {
          complete()
        }
  }

  @Test fun emptyTakeOrError() = runTest {
    emptyObservable()
        .takeOrError(1)
        .testObservable {
          error { it is NoSuchElementException && it.message == "Take wanted 1 item but saw 0" }
        }
  }

  @Test fun oneTakeOne() = runTest {
    observableOf(1)
        .take(1)
        .testObservable {
          item(1)
          complete()
        }
  }

  @Test fun oneTakeOneOrError() = runTest {
    observableOf(1)
        .takeOrError(1)
        .testObservable {
          item(1)
          complete()
        }
  }

  @Test fun twoTakeOne() = runTest {
    observableOf(1, 2)
        .take(1)
        .testObservable {
          item(1)
          complete()
        }
  }

  @Test fun twoTakeOneOrError() = runTest {
    observableOf(1, 2)
        .takeOrError(1)
        .testObservable {
          item(1)
          complete()
        }
  }
}
