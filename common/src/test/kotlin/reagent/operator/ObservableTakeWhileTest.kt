package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ObservableTakeWhileTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .takeWhile { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun takeNone() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .takeWhile { called++; false }
        .testObservable {
          complete()
        }
    assertEquals(1, called)
  }

  @Test fun takeAll() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .takeWhile { called++; true }
        .testObservable {
          item(1)
          item(2)
          item(3)
          complete()
        }
    assertEquals(3, called)
  }

  @Test fun takeThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf(1, 2, 3)
        .takeWhile { throw exception }
        .testObservable {
          error(exception)
        }
  }
}
