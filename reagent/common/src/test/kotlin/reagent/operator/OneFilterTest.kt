package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.toOne
import reagent.tester.testOne
import kotlin.test.Test
import kotlin.test.fail

class OneFilterTest {
  @Test fun filter() = runTest {
    observableOf("Hello")
        .filter { it == "Hello" }
        .testOne {
          item("Hello")
        }
  }
  @Test fun filterOut() = runTest {
    observableOf("Hello")
        .filter { it != "Hello" }
        .testOne {
          item(null)
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toOne()
        .filter { fail() }
        .testOne {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello")
        .filter { throw exception }
        .testOne {
          error(exception)
        }
  }
}
