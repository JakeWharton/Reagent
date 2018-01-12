package reagent

import reagent.operator.filter
import reagent.tester.testMany
import kotlin.test.Test

class ManyFilterTest {
  @Test fun filter() = runTest {
    Many.fromArray("Hello", "World")
        .filter { it == "Hello" }
        .testMany {
          item("Hello")
          complete()
        }
  }

  @Test fun filterEmpty() = runTest {
    Many.empty<Nothing>()
        .filter { throw AssertionError() }
        .testMany {
          complete()
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    Many.error<Nothing>(exception)
        .filter { throw AssertionError() }
        .testMany {
          error(exception)
        }
  }

  @Test fun filterThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    Many.fromArray("Hello", "World")
        .filter { throw exception }
        .testMany {
          error(exception)
        }
  }
}
