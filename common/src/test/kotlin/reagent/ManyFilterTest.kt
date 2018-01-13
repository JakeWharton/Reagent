package reagent

import reagent.operator.filter
import reagent.source.emptyMany
import reagent.source.manyOf
import reagent.source.toMany
import reagent.tester.testMany
import kotlin.test.Test
import kotlin.test.fail

class ManyFilterTest {
  @Test fun filter() = runTest {
    manyOf("Hello", "World")
        .filter { it == "Hello" }
        .testMany {
          item("Hello")
          complete()
        }
  }

  @Test fun filterEmpty() = runTest {
    emptyMany<Nothing>()
        .filter { fail() }
        .testMany {
          complete()
        }
  }

  @Test fun filterError() = runTest {
    val exception = RuntimeException("Oops!")
    exception.toMany<Nothing>()
        .filter { fail() }
        .testMany {
          error(exception)
        }
  }

  @Test fun filterThrowing() = runTest {
    val exception = RuntimeException("Oops!")
    manyOf("Hello", "World")
        .filter { throw exception }
        .testMany {
          error(exception)
        }
  }
}
