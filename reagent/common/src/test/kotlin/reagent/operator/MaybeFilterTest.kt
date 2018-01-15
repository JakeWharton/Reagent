package reagent.operator

import reagent.runTest
import reagent.source.test.emptyMaybe
import reagent.source.test.maybeOf
import reagent.source.test.toMaybe
import reagent.tester.testMaybe
import kotlin.test.Test
import kotlin.test.fail

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
        .filter { fail() }
        .testMaybe {
          nothing()
        }
  }

  @Test
  fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMaybe<Nothing>()
        .filter { fail() }
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
