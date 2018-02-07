package reagent.operator

import reagent.runTest
import reagent.source.observableOf
import reagent.source.toTask
import reagent.tester.testTask
import kotlin.test.Test
import kotlin.test.fail

class TaskFilterTest {
  @Test fun filter() = runTest {
    observableOf("Hello")
        .filter { it == "Hello" }
        .testTask {
          item("Hello")
        }
  }
  @Test fun filterOut() = runTest {
    observableOf("Hello")
        .filter { it != "Hello" }
        .testTask {
          item(null)
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toTask()
        .filter { fail() }
        .testTask {
          error(exception)
        }
  }

  @Test fun mapThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    observableOf("Hello")
        .filter { throw exception }
        .testTask {
          error(exception)
        }
  }
}
