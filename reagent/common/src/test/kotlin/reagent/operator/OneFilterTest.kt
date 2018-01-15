package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.toOne
import reagent.tester.testMaybe
import kotlin.test.Test
import kotlin.test.fail

class OneFilterTest {
  @Test fun filter() = runTest {
    observableOf("Hello")
        .filter { it == "Hello" }
        .testMaybe {
          item("Hello")
        }
  }
  @Test fun filterOut() = runTest {
    observableOf("Hello")
        .filter { it != "Hello" }
        .testMaybe {
          nothing()
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toOne<Nothing>()
        .filter { fail() }
        .testMaybe {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello")
        .filter { throw exception }
        .testMaybe {
          error(exception)
        }
  }
}
