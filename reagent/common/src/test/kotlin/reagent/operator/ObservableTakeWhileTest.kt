package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observable
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

  @Test fun takeSome() = runTest {
    observableOf(1, 2, 3)
        .takeWhile { it < 3 }
        .testObservable {
          item(1)
          item(2)
          complete()
        }
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

  @Test fun emitterReturnValue() = runTest {
    val emits = mutableListOf<Boolean>()
    observable<Int> {
      for (i in 1..2) {
        emits.add(it(i))
      }
    }.takeWhile { it < 2 }.testObservable {
      item(1)
      complete()
    }
    assertEquals(listOf(true, false), emits)
  }
}
