package reagent.operator

import reagent.runTest
import reagent.source.emptyObservable
import reagent.source.observable
import reagent.source.observableOf
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class ObservableDropWhileTest {
  @Test fun empty() = runTest {
    emptyObservable()
        .dropWhile { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun dropAll() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .dropWhile { called++; true }
        .testObservable {
          complete()
        }
    assertEquals(3, called)
  }

  @Test fun dropSome() = runTest {
    observableOf(1, 2, 3)
        .dropWhile { it < 2 }
        .testObservable {
          item(2)
          item(3)
          complete()
        }
  }

  @Test fun dropNone() = runTest {
    var called = 0
    observableOf(1, 2, 3)
        .dropWhile { called++; false }
        .testObservable {
          item(1)
          item(2)
          item(3)
          complete()
        }
    assertEquals(1, called)
  }

  @Test fun dropThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf(1, 2, 3)
        .dropWhile { throw exception }
        .testObservable {
          error(exception)
        }
  }

  @Test fun emitterReturnValue() = runTest {
    val emits = mutableListOf<Boolean>()
    observable<Int> {
      for (i in 1..4) {
        emits.add(it(i))
      }
    }.dropWhile { it < 3 }.take(1).testObservable {
      item(3)
      complete()
    }
    assertEquals(listOf(true, true, true, false), emits)
  }
}
