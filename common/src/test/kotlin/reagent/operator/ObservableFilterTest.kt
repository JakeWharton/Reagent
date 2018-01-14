package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.test.emptyMany
import reagent.source.test.toMany
import reagent.tester.testObservable
import kotlin.test.Test
import kotlin.test.fail

class ObservableFilterTest {
  @Test fun filter() = runTest {
    observableOf("Hello", "World")
        .filter { it == "Hello" }
        .testObservable {
          item("Hello")
          complete()
        }
  }

  @Test fun filterEmpty() = runTest {
    emptyMany<Nothing>()
        .filter { fail() }
        .testObservable {
          complete()
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<Nothing>()
        .filter { fail() }
        .testObservable {
          error(exception)
        }
  }

  @Test fun filterThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello", "World")
        .filter { throw exception }
        .testObservable {
          error(exception)
        }
  }
}
