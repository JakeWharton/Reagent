package reagent

import reagent.operator.filter
import reagent.source.emptyMaybe
import reagent.source.maybeOf
import reagent.source.toMaybe
import reagent.tester.testMaybe
import kotlin.test.Test

class MaybeFilterTest {
  @Test fun filter() = runTest {
    maybeOf("Hello")
        .filter { it == "Hello" }
        .testMaybe {
          item("Hello")
        }
  }

  @Test fun filterOut() = runTest {
    maybeOf("Hello")
        .filter { it != "Hello" }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterEmpty() = runTest {
    emptyMaybe<Nothing>()
        .filter { throw AssertionError() }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<Nothing>()
        .filter { throw AssertionError() }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun filterThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    maybeOf("Hello")
        .filter { throw exception }
        .testMaybe {
          error(exception)
        }
  }
}
