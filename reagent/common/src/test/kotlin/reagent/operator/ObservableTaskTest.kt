package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

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
          error {
            assertTrue(it is NoSuchElementException)
            assertEquals("Take wanted 1 item but saw 0", it.message)
          }
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

  @Test fun emitterReturnValue() = runTest {
    val emits = mutableListOf<Boolean>()
    observable<Int> {
      for (i in 1..2) {
        emits.add(it(i))
      }
    }.take(1).testObservable {
      item(1)
      complete()
    }
    assertEquals(listOf(true, false), emits)
  }
}
